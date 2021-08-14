package com.blackfat.common.utils.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:04
 * @since 1.0-SNAPSHOT
 */
public class Methods {

    public static final int FIND_IN_SUPER_CLASS_TIMES = 9;

    public static Annotation getAnnotation(Class clazz, String methodName, Predicate<Annotation> condition){
        for(int i=0; i<FIND_IN_SUPER_CLASS_TIMES; i++){
            if(clazz == null || Object.class.equals(clazz)){ return null; }
            Method method = Reflects.getFunction(clazz, methodName);
            if(method == null){ return null; }
            Annotation annotation = Annotations.getAnnotation(method, condition);
            if(annotation != null){ return annotation; }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static Annotation getAnnotation(Class clazz, String methodName, Class<? extends Annotation> annotation){
        return getAnnotation(clazz, methodName, (a)->annotation.equals(a.annotationType()));
    }

    public static boolean hasAnnotation(Class clazz, String methodName, Predicate<Annotation> condition){
        return getAnnotation(clazz, methodName, condition) != null;
    }

    public static boolean hasAnnotation(Class clazz, String methodName, Class<? extends Annotation> annotation){
        return getAnnotation(clazz, methodName, annotation) != null;
    }
}
