package com.blackfat.common.distributed.annotion;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.METHOD)
public @interface SimpleLock {

    /**
     * 方法中加锁的key
     */
    String lockKey() default "";

    /**
     * 过期时间默认10S
     */
    int expiredSeconds() default 10;

    /**
     * 重试次数 默认1
     */
    int retryCount() default 1;

    /**
     * 重试间隔 默认1S
     */
    int retryDelaySeconds() default 1;
}
