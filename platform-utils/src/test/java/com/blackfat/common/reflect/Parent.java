package com.blackfat.common.reflect;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-23 15:59
 * @since 1.0-SNAPSHOT
 */
public class Parent<T> {

    AtomicInteger updateCount = new AtomicInteger();
    private T value;

    public void setValue(T value) {
        System.out.println("Parent.setValue called");
        this.value = value;
        updateCount.incrementAndGet();
    }
}
