package com.blackfat.common.annotion;

import java.lang.annotation.*;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-05-11 09:28
 * @since 1.0-SNAPSHOT
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface BankAPI {
    String desc() default "";
    String url() default "";
}
