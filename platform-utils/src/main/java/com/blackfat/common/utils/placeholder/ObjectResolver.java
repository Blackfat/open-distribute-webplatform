package com.blackfat.common.utils.placeholder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.PropertyResolver;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * @author wangfeiyang
 * 基于Spring占位符组件的支持按key前缀路由的key值解决器
 * 支持的对象类型有Map、Properties、PropertyResolver、Bean
 * 从Bean对象取值时优先从get method获取
 * 以.分割支持多层嵌套格式
 * 对日期格式支持JsonFormat注解
 * @create 2020-04-02 15:17
 * @since 1.0-SNAPSHOT
 */
public class ObjectResolver implements PropertyPlaceholderHelper.PlaceholderResolver {

    private Object defTarget;
    private Route[] routes;
    private int size;

    /**
     * @param defTarget 默认数据提供的对象
     * @param routes    key取值对象路由
     */
    public ObjectResolver(Object defTarget, Route... routes) {
        this.defTarget = defTarget;
        if (routes == null) {
            return;
        }
        this.size = routes.length;
        this.routes = routes;
    }

    /**
     * @param defTarget 默认数据提供的对象
     * @param routes    按照路由key和路由对象顺序的路由列表
     * @return
     */
    public static ObjectResolver create(Object defTarget, Object... routes) {
        Route[] _routes = null;
        if (routes != null) {
            int size = routes.length / 2;
            _routes = new Route[size];
            for (int i = 0; i < size; i++) {
                _routes[i] = new Route((String) routes[i * 2], routes[i * 2 + 1]);
            }
        }
        return new ObjectResolver(defTarget, _routes);
    }

    private Object getTarget(String key) {
        if (routes == null) {
            return defTarget;
        }
        for (int i = 0; i < size; i++) {
            Route route = routes[i];
            if (key.startsWith(route.prefix)) {
                return route;
            }
        }
        return defTarget;
    }

    private String getter(String name) {
        return new StringBuilder("get").append(Character.toUpperCase(name.charAt(0))).append(name.substring(1)).toString();
    }


    @Override
    public String resolvePlaceholder(String key) {
        Object target = getTarget(key);
        if (target == null) {
            return null;
        } else if (target instanceof Properties) {
            return ((Properties) target).getProperty(key);
        } else if (target instanceof PropertyResolver) {
            return ((PropertyResolver) target).getProperty(key);
        } else if (target instanceof String) {
            return (String) target;
        } else if (target instanceof Route) {
            Route route = (Route) target;
            target = route.value;
            if (route.start != null) {
                key = key.substring(route.start);
            }
        }
        String[] names = StringUtils.split(key, '.');
        int size = names.length;
        Field lastField = null;
        for (int i = 0; i < size; i++) {
            String name = names[i];
            if (target == null) {
                return null;
            } else if (target instanceof Map) {
                target = ((Map) target).get(name);
            } else {
                if (name.indexOf(':') > -1) {
                    return null;
                }
                Class<?> clazz = target.getClass();
                Method method = ReflectionUtils.findMethod(clazz, getter(name));
                Field field = null;
                if (method == null) {
                    field = ReflectionUtils.findField(clazz, name);
                    if (field == null) {
                        throw new RuntimeException("[" + name + "] not found in " + clazz.getName());
                    } else {
                        ReflectionUtils.makeAccessible(field);
                        target = ReflectionUtils.getField(field, target);
                    }
                } else {
                    target = ReflectionUtils.invokeMethod(method, target);
                }
                if (i == size - 1 && target instanceof Date) {
                    if (field == null) {
                        field = ReflectionUtils.findField(clazz, name);
                    }
                    lastField = field;
                }
            }
        }
        return toString(target, lastField);
    }

    private String toString(Object target, Field field) {
        if (target == null) {
            return null;
        }
        if (target instanceof Date && field != null) {
            JsonFormat format = field.getAnnotation(JsonFormat.class);
            if (format != null) {
                String pattern = format.pattern();
                if (!StringUtils.isEmpty(pattern)) {
                    return new SimpleDateFormat(pattern).format(target);
                }
            }
        } else if (target instanceof String) {
            return (String) target;
        }
        return target.toString();
    }

    /**
     * key路由到某个对象内进行查找的描述
     */
    public static class Route {

        private String prefix;

        private Object value;

        private Integer start;


        public Route(String prefix, Object value) {
            this.prefix = prefix;
            this.value = value;
        }

        /**
         * @param prefix 以前缀开头的key路由到当前对象内进行查找
         * @param value  当前对象，key对应值的提供者
         * @param start  在当前对象内查找key时，查找key从原始key的开始位置，为null则使用原始key
         */
        public Route(String prefix, Object value, Integer start) {
            this(prefix, value);
            this.start = start;
        }

        /**
         * @param prefix   以前缀开头的key路由到当前对象内进行查找
         * @param value    当前对象，key对应值的提供者
         * @param truncate 在当前对象内查找key时，是否查找key直接从原始key按照prefix进行截断
         */
        public Route(String prefix, Object value, boolean truncate) {
            this(prefix, value);
            if (truncate) {
                this.start = prefix.length();
            }
        }

    }

    public static void main(String[] args) {
        String content = "${name},你好";
        Map<String, String> params = Maps.newHashMap();
        params.put("name","james");
        System.out.println(PlaceholderHelpers.getSpring().replacePlaceholders(content, ObjectResolver.create(params)));
    }
}
