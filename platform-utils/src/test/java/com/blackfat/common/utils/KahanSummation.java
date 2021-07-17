package com.blackfat.common.utils;

import org.junit.Test;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-05-12 10:42
 * @since 1.0-SNAPSHOT
 */
public class KahanSummation {


    @Test
    public void floatPrecisionTest() {
        float sum = 0.0f;
        for (int i = 0; i < 30000000; i++) {
            float x = 1.0f;
            sum += x;
        }
        System.out.println("sum is " + sum);

    }

    @Test
    public void kahanSummationTest() {
        float sum = 0.0f;
        float c= 0.0f;
        for (int i = 0; i < 30000000; i++) {
            float x = 1.0f;
            float y = x -c;
            float t = sum + y;
            c = t - sum - y;
            sum = t;
        }
        System.out.println("sum is " + sum);
    }


}
