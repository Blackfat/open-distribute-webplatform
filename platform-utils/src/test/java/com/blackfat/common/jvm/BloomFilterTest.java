package com.blackfat.common.jvm;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Assert;

/**
 * @author wangfeiyang
 * @desc    布隆过滤器
 * @create 2018/11/28-10:09
 */
public class BloomFilterTest {

    /*
    * -Xms64m -Xmx64m -XX:+PrintHeapAtGC
    * 10000000字节约100MB
    * */
    public static void main(String[] args) {
        long star = System.currentTimeMillis();
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                10000000,
                0.01);
        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }
        Assert.assertTrue(filter.mightContain(1));
        Assert.assertTrue(filter.mightContain(2));
        Assert.assertTrue(filter.mightContain(3));
        Assert.assertFalse(filter.mightContain(10000000));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }
}
