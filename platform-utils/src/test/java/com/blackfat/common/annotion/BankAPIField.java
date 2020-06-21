package com.blackfat.common.annotion;

import java.lang.annotation.*;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-05-11 09:29
 * @since 1.0-SNAPSHOT
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface BankAPIField {
    int order() default -1;
    int length() default -1;
    String type() default "";
}
