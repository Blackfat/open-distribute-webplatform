package com.blackfat.common.utils.collection.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/3-10:41
 */
public class LockTest {

    public  int inc = 0;

    private Lock lock = new ReentrantLock();

    public  void increase() {
       lock.lock();
       try{
           inc ++ ;
       }catch (Exception e){

       }finally {
           lock.unlock();
       }
    }

    public static void main(String[] args) throws InterruptedException {
        final LockTest test = new LockTest();
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
