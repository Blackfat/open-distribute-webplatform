package com.blackfat.spi;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/12/1-9:31
 */
public class Log4j implements  Log {
    @Override
    public void execute() {
        System.out.println("log4j ...");
    }
}
