package com.blackfat.common.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/12/18-15:19
 */
public class CacheTest {

    @Test
    public void  CacheLoaderTest(){
       LoadingCache cache = CacheBuilder.newBuilder().maximumSize(100)
               .expireAfterWrite(10, TimeUnit.MINUTES)
               // 当获取的缓存之不存在或者过期，进行缓存值的重新计算
               .build(new CacheLoader<Long, AtomicLong>() {
                   @Override
                   public AtomicLong load(Long key) throws Exception {
                       return null;
                   }
               });
    }




}
