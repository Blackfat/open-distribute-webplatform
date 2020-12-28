package com.blackfat.common.utils.spi;

import com.blackfat.common.utils.collection.CollectionUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 11:18
 * @since 1.0-SNAPSHOT
 */
public class SPIUtils {

    private static final Logger log = LoggerFactory.getLogger(SPIUtils.class);


    public static <T> T loadByFirst(Class<T> clazz){
        ServiceLoader<T> load = ServiceLoader.load(clazz);
        Iterator<T> iterator = load.iterator();
        if(iterator.hasNext()){
            return  iterator.next();
        }else{
            log.error("No class defined for: " + clazz.getName());
            throw new NoClassDefFoundError("No class defined for: " + clazz.getName());
        }
    }

    public static <T extends IOrder> T load(Class<T> clazz){
        ServiceLoader<T> load = ServiceLoader.load(clazz);
        Iterator<T> iterator = load.iterator();
        List<T> list = Lists.newArrayList();
        if(iterator.hasNext()){
            list.add(iterator.next());
        }
        list.sort(Comparator.comparingInt(T::order));
        if(CollectionUtil.isNotEmpty(list)){
            return list.get(0);
        }else{
            log.error("No class defined for: " + clazz.getName());
            throw new NoClassDefFoundError("No class defined for: " + clazz.getName());
        }
    }




}
