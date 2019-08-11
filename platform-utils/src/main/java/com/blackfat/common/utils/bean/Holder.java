package com.blackfat.common.utils.bean;

/**
 * @author wangfeiyang
 * @Description  hold a value
 * @create 2019-08-05 16:35
 * @since 1.0-SNAPSHOT
 */
public class Holder<T> {

    private volatile T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
