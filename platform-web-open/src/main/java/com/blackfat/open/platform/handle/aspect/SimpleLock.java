package com.blackfat.open.platform.handle.aspect;

import java.lang.annotation.*;

/**
 * Created by DD-PC6 on 2018/10/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.METHOD)
public @interface SimpleLock {

    /**
     * 方法中加锁的key索引
     */
    int lockKeyParameterIndex() default 0;

    /**
     * 过期时间默认5S
     */
    int expiredSeconds() default 5;

    /**
     * 重试次数 默认1
     */
    int retryCount() default 1;

    /**
     * 重试间隔 默认1S
     */
    int retryDelaySeconds() default 1;
}
