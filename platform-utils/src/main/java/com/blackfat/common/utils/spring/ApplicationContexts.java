package com.blackfat.common.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-09-07 19:11
 * @since 1.0-SNAPSHOT
 */
@Component
public class ApplicationContexts implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext applicationContext;

    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContexts.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        ApplicationContexts.environment = environment;

    }

    public static Class<?> forName(String className){
        return ClassUtils.resolveClassName(className, Thread.currentThread().getContextClassLoader());
    }

    public static ApplicationContext get() {
        return applicationContext;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static Object getBean(String name){
        return get().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return get().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz){ return get().getBean(name, clazz); }
}
