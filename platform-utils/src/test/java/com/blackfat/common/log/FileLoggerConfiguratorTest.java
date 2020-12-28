package com.blackfat.common.log;

import com.blackfat.common.utils.log.FileLoggerConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 14:28
 * @since 1.0-SNAPSHOT
 */
public class FileLoggerConfiguratorTest {
    private static final Logger logger = LoggerFactory.getLogger("BLACK_FAT");

    static {
        new FileLoggerConfigurator("./.blackfat.report", logger).config();
    }

    public static void main(String[] args) {
        logger.info("blackfat test");
    }
}
