package com.blackfat.open.platform.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author wangfeiyang
 * @desc
 * @create 2019/1/25-13:51
 */
public class LoggerTest {

    private static final Logger logger = LoggerFactory.getLogger("custom");

    @Test
    public void CustomLoggerTest(){
        logger.info("custom");
    }
}
