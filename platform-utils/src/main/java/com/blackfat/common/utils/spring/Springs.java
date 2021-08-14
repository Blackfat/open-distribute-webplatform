package com.blackfat.common.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 15:46
 * @since 1.0-SNAPSHOT
 */
@Slf4j
public class Springs extends ApplicationContexts {

    public static final SpringConfig Spring_Config = new SpringConfig();

    public static SpringConfig getConfig() {
        return Spring_Config;
    }

    public static Object findBean(String bean) {
        if (StringUtils.isEmpty(bean)) {
            return null;
        }
        if (bean.indexOf('.') > 0) {
            return getBean(forName(bean));
        } else {
            return getBean(bean);
        }
    }

    public static Object findBeanOrNew(String bean) {
        if (StringUtils.isEmpty(bean)) {
            return null;
        }
        try {
            Object o = findBean(bean);
            if (o != null) {
                return o;
            }
        } catch (Exception e) {
            log.warn("Not Found Spring Bean: " + bean);
        }
        if (bean.indexOf('.') < 0) {
            return null;
        }
        return BeanUtils.instantiateClass(forName(bean));
    }

    public static Object invoke(String bean, String method) throws InvocationTargetException, IllegalAccessException {
        Object _bean = findBean(bean);
        if (_bean == null) {
            return null;
        }
        if (StringUtils.isEmpty(method)) {
            return null;
        }
        Method _method = ReflectionUtils.findMethod(_bean.getClass(), method);
        if (_method == null) {
            return null;
        }
        return _method.invoke(_bean);
    }


}
