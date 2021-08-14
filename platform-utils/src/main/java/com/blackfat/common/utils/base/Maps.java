package com.blackfat.common.utils.base;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:11
 * @since 1.0-SNAPSHOT
 */
public class Maps {

    public static <K,V> V putIfAbsent(Map<K,V> map, K k, Supplier<V> supplier){
        V v = map.get(k);
        if(v != null){ return v; }
        v = supplier.get();
        V old = map.putIfAbsent(k, v);
        return old==null ? v:old;
    }
}
