package com.blackfat.spi;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/12/1-9:32
 */
public class Logback implements  Log {
    @Override
    public void execute() {
        System.out.println("logback ...");
    }
}
