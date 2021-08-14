package com.blackfat.common.utils.spring;

import com.alibaba.fastjson.JSONObject;
import com.blackfat.common.utils.base.*;
import com.blackfat.common.utils.bean.SingletonBeanHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 15:55
 * @since 1.0-SNAPSHOT
 */
@Slf4j
public class SpringInvokes {

    public static final Predicate<Annotation> Invokable_Predicate = (a) -> a.annotationType().getName().startsWith("com.blackfat.");

    public static ConcurrentMap<String, BeanInvokeVO> MAP = new ConcurrentHashMap<>();

    public static Supplier<ServiceLoader<InvokeApprover>> Invoke_Approvers = new SingletonBeanHolder<>(() -> {
        ServiceLoader<InvokeApprover> sl = ServiceLoader.load(InvokeApprover.class);
        sl.forEach((ia) -> log.info("Loaded " + ia.getClass().getName()));
        return sl;
    });

    public static boolean canInvoke(Object bean, Method method) {
        for (InvokeApprover ia : Invoke_Approvers.get()) {
            if (ia.approve(bean, method)) {
                return true;
            }
        }
        if (Annotations.hasAnnotation(bean.getClass(), Invokable_Predicate)) {
            return true;
        }
        if (Methods.hasAnnotation(bean.getClass(), method.getName(), Invokable_Predicate)) {
            return true;
        }
        return false;
    }

    public static BeanInvokeVO invoke(String bean, String method, Function<Class<?>, Object> argProvider, String invoker) {
        if (StringUtils.isEmpty(bean)) {
            return new BeanInvokeVO().setError("no bean");
        }
        if (StringUtils.isEmpty(method)) {
            return new BeanInvokeVO().setError("no method");
        }
        try {
            Object _bean = Springs.findBean(bean);
            if (_bean == null) {
                return new BeanInvokeVO().setError("bean not found");
            }
            Method _method = Reflects.getFunction(_bean.getClass(), method);
            if (_method == null) {
                return new BeanInvokeVO().setError("method not found");
            }
            if (!canInvoke(_bean, _method)) {
                log.warn("Invoke " + bean + "." + method + " failed: not allowed");
                return new BeanInvokeVO().setError("not allowed");
            }
            return invoke(bean, _bean, _method, argProvider, invoker);
        } catch (Throwable e) {
            log.error("Invoke " + bean + "." + method + " failed", e);
            return new BeanInvokeVO().fail(e);
        }
    }

    public static BeanInvokeVO invoke(String bean, Object beanRef, Method methodRef, Function<Class<?>, Object> argProvider, String invoker) {
        BeanInvokeVO vo = null;
        String method = methodRef.getName();
        String beanName = beanRef == null ? bean : beanRef.getClass().getName();
        CallContexts.start(beanName + "." + method);
        try {
            vo = Maps.putIfAbsent(MAP, bean + "." + method, () -> new BeanInvokeVO(bean, method));
            Object arg = null, ret;
            if (methodRef.getParameterCount() == 1) {
                if (argProvider != null) {
                    arg = argProvider.apply(methodRef.getParameterTypes()[0]);
                }
                vo.invoke(arg, invoker);
                ret = methodRef.invoke(beanRef, arg);
            } else {
                vo.invoke(null, invoker);
                ret = methodRef.invoke(beanRef);
            }
            String retVal = Jsons.toJsonString(ret);
            log.info("Invoke " + bean + "." + method + " return:" + retVal);
            vo.done(retVal);
        } catch (Throwable e) {
            if (vo == null) {
                vo = new BeanInvokeVO();
            }
            vo.fail(e);
            log.error("Invoke " + bean + "." + method + " failed", e);
        } finally {
            CallContexts.remove();
        }
        return vo;
    }

    public static BeanInvokeVO invoke(JSONObject o, String invoker) {
        return invoke(
                o.getString("bean"),
                o.getString("method"),
                (t) -> o.getObject("arg", t),
                invoker
        );
    }

}
