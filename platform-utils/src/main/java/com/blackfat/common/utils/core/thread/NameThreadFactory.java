package com.blackfat.common.utils.core.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class NameThreadFactory implements ThreadFactory {

    private final AtomicInteger  threadNumber = new AtomicInteger(1);

    private String namePrefix;


    public NameThreadFactory(String namePrefix){
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, this.namePrefix + "-thread-" + this.threadNumber.getAndIncrement());
        t.setDaemon(true);
        if (t.getPriority() != 5) {
            t.setPriority(5);
        }

        return t;
    }
}
