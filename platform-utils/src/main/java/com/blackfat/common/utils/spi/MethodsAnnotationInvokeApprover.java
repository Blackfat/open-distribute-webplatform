package com.blackfat.common.utils.spi;

import com.blackfat.common.utils.base.Methods;
import com.blackfat.common.utils.spring.InvokeApprover;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:16
 * @since 1.0-SNAPSHOT
 */
public class MethodsAnnotationInvokeApprover implements InvokeApprover {

    List<Class<? extends Annotation>> annotations;

    public MethodsAnnotationInvokeApprover() {
        this(Arrays.asList());
    }

    public MethodsAnnotationInvokeApprover(List<Class<? extends Annotation>> annotations) {
        this.annotations = annotations;
    }

    @Override
    public boolean approve(Object bean, Method method) {
        return Methods.hasAnnotation(bean.getClass(), method.getName(), (a)->annotations.contains(a.annotationType()));
    }
}
