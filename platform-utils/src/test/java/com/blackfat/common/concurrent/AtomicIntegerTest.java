package com.blackfat.common.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/3-10:35
 */
public class AtomicIntegerTest {


    public AtomicInteger inc = new AtomicInteger();

    public  void increase() {
        inc.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
        final AtomicIntegerTest test = new AtomicIntegerTest();
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase();
                    countDownLatch.countDown();
                };
            }.start();
        }

        countDownLatch.await();
        System.out.println(test.inc);
    }
}
