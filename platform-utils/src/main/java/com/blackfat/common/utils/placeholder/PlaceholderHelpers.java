package com.blackfat.common.utils.placeholder;

import org.springframework.util.PropertyPlaceholderHelper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wangfeiyang
 * @Description 基于Spring占位符组件的PlaceholderHelper工厂
 * @create 2020-04-02 15:10
 * @since 1.0-SNAPSHOT
 */
public class PlaceholderHelpers {

    private final static ConcurrentMap<String, PropertyPlaceholderHelper>
            helpers = new ConcurrentHashMap<String, PropertyPlaceholderHelper>();

    private final static PropertyPlaceholderHelper DEFAULT_HELPER = get("$[", "]", ":", false);

    private final static PropertyPlaceholderHelper SPRING_HELPER = get("${", "}", ":", false);

    public static PropertyPlaceholderHelper getDefault() {
        return DEFAULT_HELPER;
    }

    public static PropertyPlaceholderHelper getSpring() {
        return SPRING_HELPER;
    }


    public static PropertyPlaceholderHelper get(String placeholderPrefix, String placeholderSuffix, String valueSeparator, boolean ignoreUnresolvablePlaceholders) {
        String key = key(placeholderPrefix, placeholderSuffix, valueSeparator, ignoreUnresolvablePlaceholders);
        PropertyPlaceholderHelper helper = helpers.get(key);
        if (helper != null) {
            return helper;
        }
        helper = new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix, valueSeparator, ignoreUnresolvablePlaceholders);
        helpers.putIfAbsent(key, helper);
        return helper;
    }

    private static String key(String placeholderPrefix, String placeholderSuffix, String valueSeparator, boolean ignoreUnresolvablePlaceholders) {
        return new StringBuilder(placeholderPrefix).append('-').append(placeholderSuffix).append('-').append(valueSeparator).append('-').append(ignoreUnresolvablePlaceholders).toString();
    }


}
