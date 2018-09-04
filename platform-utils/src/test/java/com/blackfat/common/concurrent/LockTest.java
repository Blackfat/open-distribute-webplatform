package com.blackfat.common.concurrent;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author blackfat
 * @create 2018-06-11-下午7:56
 */
public class LockTest {

    private final int MAX_SIZE = 50;

    private LinkedList<Object> productList = new LinkedList<>();

    private final Lock lock = new ReentrantLock();

    private final Condition full = lock.newCondition();

    private final Condition empty = lock.newCondition();

    public void produce(Object o){
        try{
            lock.lock();
            while(productList.size() == MAX_SIZE){
                System.out.println("仓库已满，暂时不能生产");
                try {
                    full.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            productList.addFirst(o);
            System.out.println("已经生产成功，当前仓库存量："+productList.size());
            empty.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void consume(){
        try{
            lock.lock();
            while(productList.isEmpty()){
                System.out.println("仓库已空，暂时不能继续消费");
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Object object = productList.remove();
            System.out.println("已经消费成功，当前仓库存量："+productList.size());
            full.signalAll();
        }finally {
            lock.unlock();
        }
    }

}
