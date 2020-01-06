package com.blackfat.common.utils.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * @author wangfeiyang
 * @Description 带有刷新机制的Holder基类，首次构建是懒加载的
 * @param <E> 每次刷新需要的参数类型
 * @param <T> 需要的组件类型
 * @create 2019-12-31 13:50
 * @since 1.0-SNAPSHOT
 */
@Slf4j
public abstract class AbstractFreshBeanHolder<E, T> implements BeanHolder<T> {


    /**
     * 组件的构建器
     */
    Function<E, T> builder;

    /**
     * 组件值
     */
    protected T current;

    /**
     *首次创建组件的初始参数
     */
    protected E initArg;

    /**
     * @param builder 创建组件的构建器
     * @param initArg 首次创建组件的初始参数
     */
    public AbstractFreshBeanHolder(Function<E, T> builder, E initArg) {
        this.builder = builder;
        this.initArg = initArg;
    }

    protected E getInitArg(){ return initArg; }

    @Override
    public T get() {
        if(current != null){ return current; }
        return init();
    }

    @Override
    public void set(T fresh) {
        synchronized (this) {
            log.info("doSet "+fresh);
            this.current = fresh;
        }
    }

    protected void doFresh(E e) {
        T fresh = builder.apply(e);
        if(fresh != null){
            synchronized (this) {
                log.info("doFresh "+fresh.getClass().getName());
                this.current = fresh;
            }
        }
    }

    synchronized T init(){
        if(current != null){ return current; }
        T _t = builder.apply(getInitArg());
        current = _t;
        return _t;
    }









}
