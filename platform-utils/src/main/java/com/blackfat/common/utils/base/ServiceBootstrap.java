package com.blackfat.common.utils.base;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-08-23 15:33
 * @since 1.0-SNAPSHOT
 */
public class ServiceBootstrap {

    public static <T> Iterator<T> loadAll(Class<T> clazz){
        ServiceLoader<T> loader = ServiceLoader.load(clazz);
        return loader.iterator();
    }


    public static <T> T loadFirst(Class<T> clazz){
        Iterator<T> iterator = loadAll(clazz);
        if(!iterator.hasNext()){
            throw new IllegalStateException(String.format(
                    "No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
                    clazz.getName()));
        }
        return iterator.next();

    }




}
