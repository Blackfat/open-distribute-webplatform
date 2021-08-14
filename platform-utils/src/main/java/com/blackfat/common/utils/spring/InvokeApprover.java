package com.blackfat.common.utils.spring;

import java.lang.reflect.Method;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:00
 * @since 1.0-SNAPSHOT
 */
public interface InvokeApprover {

    /**
     * 执行调用
     * @param bean
     * @param method
     * @return
     */
    boolean approve(Object bean, Method method);
}
