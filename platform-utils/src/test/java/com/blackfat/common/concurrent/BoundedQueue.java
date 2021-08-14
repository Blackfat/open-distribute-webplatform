package com.blackfat.common.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-07-25 09:14
 * @since 1.0-SNAPSHOT
 */
public class BoundedQueue<T> {

    private Object[] items;

    private int addIndx, removeIndex, count;

    private ReentrantLock lock;

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();


    public BoundedQueue(int size){
        this.items = new Object[size];
    }

    public void add(T t) throws InterruptedException {
        lock.lock();
        try{
            while(count == items.length)
                notFull.await();
            items[addIndx] = t;
            if(addIndx++ == items.length){
                addIndx = 0;
            }
            count++;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public T remove() throws InterruptedException {
        lock.lock();
        try{
            while(count == 0)
                notEmpty.await();
            Object x = items[removeIndex];
            if(removeIndex++ == items.length){
                removeIndex = 0;
            }
            count --;
            notFull.signal();
            return (T)x;
        }finally {
            lock.unlock();
        }
    }
}
