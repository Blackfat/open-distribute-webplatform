package com.blackfat.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/12/1-9:36
 */
public class Main {

    public static void main(String[] args) {
        ServiceLoader<Log> serviceLoader = ServiceLoader.load(Log.class);
        Iterator<Log> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Log log = iterator.next();
            log.execute();
        }
    }
}
