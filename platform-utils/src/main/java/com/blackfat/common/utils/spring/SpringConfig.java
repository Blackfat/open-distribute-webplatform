package com.blackfat.common.utils.spring;

import com.blackfat.common.utils.Converters;
import com.blackfat.common.utils.bean.SingletonBeanHolder;
import org.springframework.core.env.Environment;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-09-07 19:09
 * @since 1.0-SNAPSHOT
 */
public class SpringConfig {

    Supplier<Environment> e = new SingletonBeanHolder(() ->ApplicationContexts.getEnvironment());

    SpringConfig(){}

    public String getProperty(String s, String s1) {
        return e.get().getProperty(s, s1);
    }

    public Integer getIntProperty(String s, Integer integer) {
        return e.get().getProperty(s, Integer.class, integer);
    }

    public Long getLongProperty(String s, Long aLong) {
        return e.get().getProperty(s, Long.class, aLong);
    }

    public Short getShortProperty(String s, Short aShort) {
        return e.get().getProperty(s, Short.class, aShort);
    }

    public Float getFloatProperty(String s, Float aFloat) {
        return e.get().getProperty(s, Float.class, aFloat);
    }

    public Double getDoubleProperty(String s, Double aDouble) {
        return e.get().getProperty(s, Double.class, aDouble);
    }

    public Byte getByteProperty(String s, Byte aByte) {
        return e.get().getProperty(s, Byte.class, aByte);
    }

    public Boolean getBooleanProperty(String s, Boolean aBoolean) {
        return e.get().getProperty(s, Boolean.class, aBoolean);
    }
    public String[] getArrayProperty(String s, String s1, String[] strings) {
        return getProperty(s,Converters.toArray(s1) ,strings);
    }

    public <T extends Enum<T>> T getEnumProperty(String s, Class<T> aClass, T t) {
        String v = e.get().getProperty(s);
        return v==null ? t:Enum.valueOf(aClass, v);
    }

    public <T> T getProperty(String s, Function<String, T> function, T t) {
        String v = e.get().getProperty(s);
        return v==null ? t:function.apply(v);
    }
}
