package com.blackfat.common.utils.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * @author wangfeiyang
 * @Description 带有实时加载并转换机制的基类
 * @param <T> 需要的组件类型
 * @create 2019-12-31 13:48
 * @since 1.0-SNAPSHOT
 */
@Slf4j
public abstract class AbstractValueBeanHolder<T> implements BeanHolder<T> {

    /**
     * 转换function
     */
    Function<String, T> converter;

    /**
     * coverter默认值
     */
    T defVal;

    /**
     * coverter当前值
     */
    T curVal;

    /**
     * 当前值
     */
    String curStr;


    public AbstractValueBeanHolder(Function<String, T> converter, T defVal) {
        this.converter = converter;
        this.defVal = defVal;
    }


    @Override
    public T get() {
        try {
            String val = load();
            if (val == null) {
                return defVal;
            }
            if (converter == null) {
                return (T) val;
            }
            if (curVal != null && val.equals(curStr)) {
                return curVal;
            }
            T _val = converter.apply(val);
            curVal = _val;
            curStr = val;
            return _val;
        } catch (Throwable e) {
            log.warn("", e);
            return curVal == null ? defVal : curVal;
        }
    }


    /**
     * 加载value
     * @return
     */
    protected abstract String load();

}
