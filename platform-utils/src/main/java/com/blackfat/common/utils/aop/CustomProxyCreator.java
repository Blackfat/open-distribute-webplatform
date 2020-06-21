package com.blackfat.common.utils.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.target.EmptyTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 *
 */
@Slf4j
@Component
public class CustomProxyCreator extends AbstractAutoProxyCreator implements DisposableBean {

    MethodInterceptor interceptor;
    static final Set<String> PROXYED_SET = new HashSet<>();

    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        try {
            synchronized (PROXYED_SET) {
                if (Objects.isNull(bean)) {
                    return bean;
                }
                if (PROXYED_SET.contains(beanName)) {
                    return bean;
                }
                Class<?> serviceInterface = findTargetClass(bean);
                Class<?>[] interfacesIfJdk = findInterfaces(bean);
                if (interceptor == null) {
                    interceptor = new CustomInterceptor();
                }
                if(!super.isProxyTargetClass()){
                    //强制使用cglib，否则会报错
                    super.setProxyTargetClass(true);
                }
                logger.info("Bean[" + bean.getClass().getName() + "] with name [" + beanName
                        + "] would use interceptor [" + interceptor.getClass().getName() + "]");
                if (!AopUtils.isAopProxy(bean)) {
                    return super.wrapIfNecessary(bean, beanName, cacheKey);
                } else {
                    AdvisedSupport advised = getAdvisedSupport(bean);
                    // 添加环绕增强
                    Advisor[] advisor = buildAdvisors(beanName, getAdvicesAndAdvisorsForBean(null, null, null));
                    for (Advisor avr : advisor) {
                        advised.addAdvisor(0, avr);
                    }
                }
                PROXYED_SET.add(beanName);
                return bean;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {
        return new Object[]{interceptor};
    }

    @Override
    public void destroy() throws Exception {

    }

    private Class<?> findTargetClass(Object proxy) throws Exception {
        if (AopUtils.isAopProxy(proxy)) {
            AdvisedSupport advised = getAdvisedSupport(proxy);
            if (AopUtils.isJdkDynamicProxy(proxy)) {
                TargetSource targetSource = advised.getTargetSource();
                return targetSource instanceof EmptyTargetSource ? getFirstInterfaceByAdvised(advised)
                        : targetSource.getTargetClass();
            }
            Object target = advised.getTargetSource().getTarget();
            return findTargetClass(target);
        } else {
            return proxy == null ? null : proxy.getClass();
        }
    }

    public static Class<?>[] findInterfaces(Object proxy) throws Exception {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            AdvisedSupport advised = getAdvisedSupport(proxy);
            return getInterfacesByAdvised(advised);
        } else {
            return null;
        }
    }

    private static Class<?>[] getInterfacesByAdvised(AdvisedSupport advised) {
        Class<?>[] interfaces = advised.getProxiedInterfaces();
        if (interfaces.length > 0) {
            return interfaces;
        } else {
            throw new IllegalStateException("Find the jdk dynamic proxy class that does not implement the interface");
        }
    }

    // 获取当前代理
    public static AdvisedSupport getAdvisedSupport(Object proxy) throws Exception {
        Field h;
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            h = proxy.getClass().getSuperclass().getDeclaredField("h");
        } else {
            h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        }
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return (AdvisedSupport) advised.get(dynamicAdvisedInterceptor);
    }

    private static Class<?> getFirstInterfaceByAdvised(AdvisedSupport advised) { // jdk的动态代理必须是实现了接口
        Class<?>[] interfaces = advised.getProxiedInterfaces();
        if (interfaces.length > 0) {
            return interfaces[0];
        } else {
            throw new IllegalStateException("Find the jdk dynamic proxy class that does not implement the interface");
        }
    }
}
