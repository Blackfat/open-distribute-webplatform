package com.blackfat.common.utils.sensitive;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-16 19:42
 * @since 1.0-SNAPSHOT
 */
public interface SensitiveType {
    /**
     * 敏感类型
     * @return
     */
    String name();

    /**
     * 检测是否敏感数据，尽量进行性能优化
     * @param k
     * @param s
     * @return
     */
    boolean match(String k, String s);

    /**
     * 对数据脱敏，尽量进行性能优化
     * @param s
     * @return
     */
    String desensitize(String s);
}
