package com.blackfat.common.utils.base;

import com.blackfat.common.utils.Threads;
import com.blackfat.common.utils.bean.SingletonBeanHolder;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:51
 * @since 1.0-SNAPSHOT
 */
public class Schedulers {

    public static final SingletonBeanHolder<ScheduledExecutorService> HOLDER = new SingletonBeanHolder<>(()->{
        int poolSize = 1;
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(poolSize, Threads.buildJobFactory("Scheduler"));
        return scheduler;
    });

    public static ScheduledExecutorService get(){
        return HOLDER.get();
    }
}
