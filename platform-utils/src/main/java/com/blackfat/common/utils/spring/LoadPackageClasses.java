package com.blackfat.common.utils.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 16:16
 * @since 1.0-SNAPSHOT
 */
public class LoadPackageClasses {

    protected final Log logger = LogFactory.getLog(getClass());

    private static final String RESOURCE_PATTERN = "/**/*.class";

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private List<String> packagesList = new LinkedList<String>();

    private List<TypeFilter> typeFilters = new LinkedList<TypeFilter>();

    private Set<Class<?>> classSet = new HashSet<Class<?>>();

    /**
     * 构造函数
     *
     * @param packagesToScan   指定哪些包需要被扫描,支持多个包"package.a,package.b"并对每个包都会递归搜索
     * @param annotationFilter 指定扫描包中含有特定注解标记的bean,支持多个注解
     */
    public LoadPackageClasses(String[] packagesToScan, Class<? extends Annotation>... annotationFilter) {
        if (packagesToScan != null) {
            for (String packagePath : packagesToScan) {
                this.packagesList.add(packagePath);
            }
        }
        if (annotationFilter != null) {
            for (Class<? extends Annotation> annotation : annotationFilter) {
                typeFilters.add(new AnnotationTypeFilter(annotation, false));
            }
        }
    }

    /**
     * 将符合条件的Bean以Class集合的形式返回
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Set<Class<?>> getClassSet() throws IOException, ClassNotFoundException {
        this.classSet.clear();
        if (!this.packagesList.isEmpty()) {
            for (String pkg : this.packagesList) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
                Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        String className = reader.getClassMetadata().getClassName();
                        if (matchesEntityTypeFilter(reader, readerFactory)) {
                            this.classSet.add(Class.forName(className));
                        }
                    }
                }
            }
        }
        //输出日志
        for (Class<?> clazz : this.classSet) {
            logger.debug(String.format("Found class:%s", clazz.getName()));
        }

        return this.classSet;
    }

    /**
     * @return 满足条件的
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Set<Class<?>> getClassSetAndChecked(Class<? extends Annotation> annotatiion) throws IOException, ClassNotFoundException {
        this.classSet.clear();
        if (!this.packagesList.isEmpty()) {
            for (String pkg : this.packagesList) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
                Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        String className = reader.getClassMetadata().getClassName();
                        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
                        boolean classNameAnnotationCheck = true;
                        if (annotationMetadata != null) {
                            Map<String, Object> aMap = annotationMetadata.getAnnotationAttributes(annotatiion.getName());
                            if (aMap != null) {
                                for (Map.Entry<String, Object> entry : aMap.entrySet()) {
                                    if (!isArrayAndValueExists(entry.getValue())) {
                                        classNameAnnotationCheck = false;
                                    }
                                }
                            }
                        }
                        if (classNameAnnotationCheck && matchesEntityTypeFilter(reader, readerFactory)) {
                            this.classSet.add(Class.forName(className));
                        }
                    }
                }
            }
        }
        //输出日志
        for (Class<?> clazz : this.classSet) {
            logger.debug(String.format("Found class:%s", clazz.getName()));
        }

        return this.classSet;
    }

    /**
     * 检查当前扫描到的Bean含有任何一个指定的注解标记
     *
     * @param reader
     * @param readerFactory
     * @return
     * @throws IOException
     */
    private boolean matchesEntityTypeFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
        if (!this.typeFilters.isEmpty()) {
            for (TypeFilter filter : this.typeFilters) {
                if (filter.match(reader, readerFactory)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isArrayAndValueExists(Object obj) {
        if (obj == null) {
            return false;
        }
        boolean isArray = obj.getClass().isArray();
        if (!isArray) {
            return false;
        }
        Object[] arrayObject = (Object[]) obj;
        for (Object item : arrayObject) {
            if (item instanceof Class) {
                Class cls = (Class) item;
                if (!ClassUtils.isPresent(cls.getName(), this.getClass().getClassLoader())) {
                    return false;
                }
            } else if (item instanceof String) {
                if (!ClassUtils.isPresent((String) item, this.getClass().getClassLoader())) {
                    return false;
                }
            }
        }
        return true;

    }
}
