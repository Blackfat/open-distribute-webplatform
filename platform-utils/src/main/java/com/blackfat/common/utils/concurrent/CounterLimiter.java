package com.blackfat.common.utils.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangfeiyang
 * @Description 滑动窗口
 * @create 2021-04-06 09:29
 * @since 1.0-SNAPSHOT
 */
public class CounterLimiter extends RateLimiter {

    // 计数器
    private volatile int counter = 0;

    // 上次计数时间
    private volatile long lastTime = 0;

    // cas自旋锁
    private Lock lock = new ReentrantLock();

    public CounterLimiter(int rate) {
        super(rate);
    }



    @Override
    public boolean tryAcquire() {
        lock.lock();
        try{
            // 是否已经过期了
            if(System.currentTimeMillis() - lastTime > 1000){
                counter = 0;
                lastTime = System.currentTimeMillis();
            }
            // 计数
            counter ++;

            // 是否超过速率
            if (counter <= rate){
                return true;
            }
        }finally {
            lock.unlock();
        }
        return false;
    }
}
