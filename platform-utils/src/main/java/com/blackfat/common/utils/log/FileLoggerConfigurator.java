package com.blackfat.common.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 14:21
 * @since 1.0-SNAPSHOT
 */
public class FileLoggerConfigurator implements LoggerConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileLoggerConfigurator.class);
    private static final String logbackClassName = "ch.qos.logback.classic.Logger";
    private static final String log4jClassName = "org.slf4j.impl.Log4jLoggerAdapter";
    private static final String log4j2ClassName = "org.apache.logging.slf4j.Log4jLogger";
    private String logPath;
    private Logger loggerToConfig;
    private boolean simple = false;

    public FileLoggerConfigurator(String logPath, Logger loggerToConfig) {
        this.logPath = logPath;
        this.loggerToConfig = loggerToConfig;
    }

    public FileLoggerConfigurator(String logPath, Logger loggerToConfig, boolean simple) {
        this.logPath = logPath;
        this.loggerToConfig = loggerToConfig;
        this.simple = simple;
    }


    @Override
    public void config() {
        String loggerClassName = loggerToConfig.getClass().getName();
        try {
            if (log4j2ClassName.equals(loggerClassName)) {
               // todo
            } else if (logbackClassName.equals(loggerClassName)) {
                LogbackFileLoggerConfigStrategy strategy = new LogbackFileLoggerConfigStrategy(logPath);
                if (simple) {
                    strategy.setFormat("%m%n");
                }
                strategy.config((ch.qos.logback.classic.Logger)loggerToConfig);
            } else if (log4jClassName.equals(loggerClassName)) {
                // todo
            } else {
                LOGGER.warn("不支持配置'{}'该类型的logger，日志打印最终将取决于用户的配置。目前只支持log4j2、logback、log4j的配置", loggerClassName);
            }
        } catch (LoggerConfigException e) {
            LOGGER.error("配置logger[{}]失败，原因：{}", loggerClassName, e.getMessage());
        }
    }
}
