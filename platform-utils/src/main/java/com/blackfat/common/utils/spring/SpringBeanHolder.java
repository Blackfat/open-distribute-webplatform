package com.blackfat.common.utils.spring;

import com.blackfat.common.utils.bean.BeanHolder;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-09-07 19:20
 * @since 1.0-SNAPSHOT
 */
public class SpringBeanHolder<T> implements BeanHolder<T> {
    T bean;
    Class<T> clazz;
    String name;

    public SpringBeanHolder(String name, Class<T> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public SpringBeanHolder(Class<T> clazz) {
        this(null, clazz);
    }

    public SpringBeanHolder(String name) {
        this(name, null);
    }



    @Override
    public T get() {
        if(bean != null){ return bean; }
        T t = init();
        bean = t;
        return t;
    }

    T init(){
        if(name!=null && clazz!=null){
            return ApplicationContexts.getBean(name, clazz);
        }else if(clazz != null){
            return ApplicationContexts.getBean(clazz);
        }else if(name != null){
            return (T) ApplicationContexts.getBean(name);
        }else {
            return null;
        }
    }
}
