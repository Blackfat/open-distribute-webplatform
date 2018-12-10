package com.blackfat.common.concurrent;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/12/10-9:16
 */
public class ProductWithLock {

    public static void main(String[] args) {
        Workman workman = new Workman();

        Thread producerThread = new Thread(new Producer1(workman));

        Thread consumerThread = new Thread(new Consumer1(workman));

        producerThread.start();

        consumerThread.start();
    }



}
class Workman {
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

class Producer1 implements Runnable {

    private Workman workman;

    public Producer1(Workman workman) {
        this.workman = workman;
    }

    @Override
    public void run() {
        System.out.println("生产者开始生产商品");

        while (true) {
            try {
                Thread.sleep((int) (Math.random() * 10) * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            workman.produce(new Object());
        }
    }

}

class Consumer1 implements Runnable {

    private Workman workman;

    public Consumer1(Workman workman) {
        this.workman = workman;
    }

    @Override
    public void run() {
        System.out.println("消费者开始取走商品");
        while (true) {
            try {
                Thread.sleep((int) (Math.random() * 10) * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            workman.consume();
        }
    }

}