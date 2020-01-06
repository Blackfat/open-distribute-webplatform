package com.blackfat.common.utils.bean;

import java.util.function.Supplier;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-12-31 13:44
 * @since 1.0-SNAPSHOT
 */
public interface BeanHolder<T> extends Supplier<T> {

    default void set(T t){
        throw new UnsupportedOperationException();
    }
}
