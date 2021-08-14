package com.blackfat.common.utils.daemonjob;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author wangfeiyang
 * @Description 守护job的类
 * @create 2021-08-10 16:25
 * @since 1.0-SNAPSHOT
 */
@Data
public class DaemonJobBO {

    String bean;
    Object beanRef;
    Method method;
    DaemonJob annotation;

    /**
     * @param bean spring中的id或类全名
     * @param beanRef bean的实例
     * @param method 守护方法
     * @param annotation 守护注解实例
     */
    public DaemonJobBO(String bean, Object beanRef, Method method, DaemonJob annotation){
        this.bean = bean;
        this.beanRef = beanRef;
        this.method = method;
        this.annotation = annotation;
    }
}
