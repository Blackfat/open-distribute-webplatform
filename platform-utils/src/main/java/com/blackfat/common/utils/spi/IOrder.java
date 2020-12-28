package com.blackfat.common.utils.spi;

/**
 * @author wangfeiyang
 * @Description 排序接口
 * @create 2020-12-28 11:17
 * @since 1.0-SNAPSHOT
 */
public interface IOrder {

    int HIGHEST_PRECEDENCE = -2147483648;

    int LOWEST_PRECEDENCE = 2147483647;

    default int order() {
        return 0;
    }
}
