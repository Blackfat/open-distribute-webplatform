package com.blackfat.common.utils.thead;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-16 14:04
 * @since 1.0-SNAPSHOT
 */
public class ThreadFactoryImpl implements ThreadFactory {
    private AtomicLong threadIndex = new AtomicLong(0);
    private final String threadNamePrefix;
    private final boolean daemon;


    public ThreadFactoryImpl(final String threadNamePrefix) {
        this(threadNamePrefix, false);
    }

    public ThreadFactoryImpl(final String threadNamePrefix, boolean daemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.daemon = daemon;
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,threadNamePrefix+this.threadIndex.incrementAndGet());
        return thread;
    }
}
