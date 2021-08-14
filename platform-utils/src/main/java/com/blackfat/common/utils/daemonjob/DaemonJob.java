package com.blackfat.common.utils.daemonjob;

import java.lang.annotation.*;

/**
 * @author wangfeiyang
 * @Description
 * 守护job注解
 * 只对被spring管理的bean生效
 * 调度器会按照注解上的参数进行调度
 * @create 2021-08-10 16:26
 * @since 1.0-SNAPSHOT
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DaemonJob {

    /**
     * 是否同步(使用main线程)启动，否则系统启动时通过单独的线程(调度器)进行首次调度
     */
    boolean syncBoot() default true;

    /**
     * 下次执行的延迟时间，单位秒，<1采用系统默认900秒
     */
    long delay() default 0L;

}
