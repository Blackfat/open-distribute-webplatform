package com.blackfat.common.utils.log;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.*;
import ch.qos.logback.core.util.FileSize;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 14:17
 * @since 1.0-SNAPSHOT
 */
public class LogbackFileLoggerConfigStrategy implements LoggerConfigStrategy<ch.qos.logback.classic.Logger> {
    private static final String FORMAT = "[%d{yyyy-MM-dd HH:mm:ss.SSS}][%p][%t][%c{1}][%X{traceId},%X{spanId},%X{parentSpanId}] - %m%n";
    private String logPath;
    private String format = FORMAT;

    public LogbackFileLoggerConfigStrategy(String logPath) {
        this.logPath = logPath;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void config(Logger logger) throws LoggerConfigException {
        try {
            logger.setAdditive(false);

            PatternLayoutEncoder ple = new PatternLayoutEncoder();
            ple.setPattern(format);
            ple.setContext(logger.getLoggerContext());
            ple.start();

            RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
            fileAppender.setFile(logPath);
            fileAppender.setAppend(true);
            fileAppender.setContext(logger.getLoggerContext());
            fileAppender.setEncoder(ple);

            RollingPolicy rollingPolicy = getFixedWindowRollingPolicy(logger);
            rollingPolicy.setParent(fileAppender);
            rollingPolicy.start();
            TriggeringPolicy<ILoggingEvent> triggeringPolicy = getSizeBasedTriggeringPolicy(logger);
            triggeringPolicy.start();

            fileAppender.setRollingPolicy(rollingPolicy);
            fileAppender.setTriggeringPolicy(triggeringPolicy);
            fileAppender.start();

            logger.addAppender(fileAppender);
        } catch (Exception e) {
            throw new LoggerConfigException(e.getMessage());
        }
    }

    private SizeBasedTriggeringPolicy getSizeBasedTriggeringPolicy(ch.qos.logback.classic.Logger logbackLogger) {
        SizeBasedTriggeringPolicy triggeringPolicy = new SizeBasedTriggeringPolicy();
        triggeringPolicy.setMaxFileSize(FileSize.valueOf("50MB"));
        triggeringPolicy.setContext(logbackLogger.getLoggerContext());
        return triggeringPolicy;
    }

    private FixedWindowRollingPolicy getFixedWindowRollingPolicy(ch.qos.logback.classic.Logger logbackLogger) {
        FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
        rollingPolicy.setMinIndex(1);
        rollingPolicy.setMaxIndex(3);
        rollingPolicy.setContext(logbackLogger.getLoggerContext());
        rollingPolicy.setFileNamePattern(logPath + "_%i");
        return rollingPolicy;
    }
}
