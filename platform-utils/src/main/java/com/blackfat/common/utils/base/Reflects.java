package com.blackfat.common.utils.base;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:04
 * @since 1.0-SNAPSHOT
 */
public class Reflects {

    public static Object getFieldValue(Object target, String fieldName) {
        if (target == null) {
            return null;
        }
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        if (field == null) {
            return null;
        }
        if(!field.isAccessible()){ ReflectionUtils.makeAccessible(field); }
        Object val = ReflectionUtils.getField(field, target);
        return val;
    }

    public static <T> T getFieldValue(Object target, String fieldName, Class<T> fieldType) {
        Object val = getFieldValue(target, fieldName);
        if (val == null) {
            return null;
        }
        if (fieldType.isAssignableFrom(val.getClass())) { return (T) val; }
        return null;
    }

    public static void setFieldValue(Object target, String fieldName, Object fieldVal) {
        if (target == null) { return ; }
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        if (field == null) { return ; }
        if(!field.isAccessible()){ ReflectionUtils.makeAccessible(field); }
        ReflectionUtils.setField(field, target, fieldVal);
    }

    public static Method getFunction(Class<?> clazz, String method){
        Method[] ms = clazz.getMethods();
        Method def = null;
        for(Method m:ms){
            if(method.equals(m.getName())){
                Class<?>[] types = m.getParameterTypes();
                boolean found = true;
                for(Class<?> type:types){
                    if(type.equals(Object.class)){ found = false; }
                }
                if(found){ return m; }
                def = m;
            }
        }
        return def;
    }


    public static Object invokeMethod(Object target, String methodName, boolean findByArgs, Object... params) {
        if (target == null) { return null; }
        Class<?>[] types = null;
        if(findByArgs && params != null){
            int l = params.length;
            types = new Class<?>[l];
            for(int i=0; i<l; i++){ types[i] = params[i].getClass(); }
        }
        Method method = ReflectionUtils.findMethod(target.getClass(), methodName, types);
        if (method == null) { return null; }
        return ReflectionUtils.invokeMethod(method, target, params);
    }

    public static Object invokeMethod(Object target, String methodName, Object... params) {
        return invokeMethod(target, methodName, false, params);
    }


    public static Type getActualTypeArg(Object that, int index){
        Type superClass = that.getClass().getGenericSuperclass();
        return ((ParameterizedType)superClass).getActualTypeArguments()[index];
    }

    public static Type getActualTypeArg(Object that){
        return getActualTypeArg(that, 0);
    }
}
