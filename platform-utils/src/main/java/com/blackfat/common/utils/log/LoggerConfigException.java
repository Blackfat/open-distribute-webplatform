package com.blackfat.common.utils.log;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 14:14
 * @since 1.0-SNAPSHOT
 */
public class LoggerConfigException extends Exception {

    public LoggerConfigException(String message) {
        super(message);
    }

    public LoggerConfigException(Throwable cause) {
        super(cause);
    }
}
