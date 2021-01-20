package com.blackfat.common.concurrent;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-01-04 19:33
 * @since 1.0-SNAPSHOT
 */
public interface ThreadPool<Job extends Runnable> {

    void execute(Job job);

    void shutdown();

    Integer getJobSize();
}
