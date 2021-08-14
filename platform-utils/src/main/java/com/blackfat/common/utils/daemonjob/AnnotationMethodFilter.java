package com.blackfat.common.utils.daemonjob;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:29
 * @since 1.0-SNAPSHOT
 */
public class AnnotationMethodFilter implements ReflectionUtils.MethodFilter {

    Class<? extends Annotation> annotationClass;

    public AnnotationMethodFilter(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public boolean matches(Method method) {
        return method.getAnnotation(annotationClass) != null;
    }
}
