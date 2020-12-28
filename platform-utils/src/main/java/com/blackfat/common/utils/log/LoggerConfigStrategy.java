package com.blackfat.common.utils.log;

import org.slf4j.Logger;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 14:15
 * @since 1.0-SNAPSHOT
 */
public interface LoggerConfigStrategy<T extends Logger> {

    void config(T logger) throws LoggerConfigException;
}
