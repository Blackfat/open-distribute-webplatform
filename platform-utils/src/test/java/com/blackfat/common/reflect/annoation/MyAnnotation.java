package com.blackfat.common.reflect.annoation;

import java.lang.annotation.*;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-23 16:46
 * @since 1.0-SNAPSHOT
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyAnnotation {

    String value();
}
