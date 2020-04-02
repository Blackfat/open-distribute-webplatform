package com.blackfat.common.eventBus.diy;

import java.util.concurrent.Executor;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-03-16 09:29
 * @since 1.0-SNAPSHOT
 */
public class AsyncEventBus extends EventBus {

    public AsyncEventBus(Executor executor) { super(executor); }
}
