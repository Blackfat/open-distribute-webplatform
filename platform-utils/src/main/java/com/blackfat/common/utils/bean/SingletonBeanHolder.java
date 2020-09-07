package com.blackfat.common.utils.bean;

import java.util.function.Supplier;

/**
 * @author wangfeiyang
 * @Description 可保证单例及懒加载的Holder
 * @create 2020-09-07 19:03
 * @since 1.0-SNAPSHOT
 */
public class SingletonBeanHolder<T> implements BeanHolder<T> {
    Supplier<T> supplier;
    T t;

    public SingletonBeanHolder(Supplier<T> supplier) {
        this.supplier = supplier;
    }


    @Override
    public T get() {
        if(t != null){ return t; }
        return init();
    }

    synchronized T init(){
        if(t != null){ return t; }
        T _t = supplier.get();
        t = _t;
        return _t;
    }
}
