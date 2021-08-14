package com.blackfat.common.utils.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.function.Predicate;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:03
 * @since 1.0-SNAPSHOT
 */
public class Annotations {

    public static Annotation getAnnotation(AnnotatedElement annotatedElement, Predicate<Annotation> condition){
        Annotation[] annotations = annotatedElement.getAnnotations();
        for(Annotation annotation:annotations){
            if(condition.test(annotation)){ return annotation; }
        }
        return null;
    }

    public static boolean hasAnnotation(AnnotatedElement annotatedElement, Predicate<Annotation> condition){
        return getAnnotation(annotatedElement, condition) != null;
    }
}
