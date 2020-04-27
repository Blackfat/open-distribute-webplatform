package com.blackfat.common.reflect.annoation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-23 16:51
 * @since 1.0-SNAPSHOT
 */
@Slf4j
public class Main {


    private static String getAnnotationValue(MyAnnotation annotation) {
        if (annotation == null) return "";
        return annotation.value();
    }

    public static void right() throws NoSuchMethodException {

        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass(), MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(AnnotatedElementUtils.findMergedAnnotation(child.getClass().getMethod("invoke"), MyAnnotation.class)));
    }


    public static void wrong() throws NoSuchMethodException {
        //获取父类的类和方法上的注解
        Parent parent = new Parent();
        log.info("ParentClass:{}", getAnnotationValue(parent.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ParentMethod:{}", getAnnotationValue(parent.getClass().getMethod("invoke").getAnnotation(MyAnnotation.class)));

        //获取子类的类和方法上的注解
        Child child = new Child();
        log.info("ChildClass:{}", getAnnotationValue(child.getClass().getAnnotation(MyAnnotation.class)));
        log.info("ChildMethod:{}", getAnnotationValue(child.getClass().getMethod("invoke").getAnnotation(MyAnnotation.class)));
    }

    public static void main(String[] args) throws NoSuchMethodException {
        wrong();
        right();
    }
}
