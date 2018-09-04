package com.blackfat.common.concurrent;

/**
 * @author blackfat
 * @create 2018-09-04-下午9:40
 */
public class ProductTest {


    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Thread producerThread = new Thread(new Producer(clerk));

        Thread consumerThread = new Thread(new Consumer(clerk));

        producerThread.start();

        consumerThread.start();

    }
}

/**
 * 店员
 *
 * @author DD-PC6
 * @version $Id: ProductTest.java, v 0.1 2016年8月27日 上午10:06:36 DD-PC6 Exp $
 */
class Clerk {
    /**
     * 最大产品数量
     */
    private static final int MAX_PRODUCT = 20;

    /**
     * 最小产品数量
     */
    private static final int MIN_PRODUCT = 0;

    /**
     * 产品数量
     */
    private int product = 0;

    /**
     * 添加产品
     */
    public synchronized void addProduct() {
        while (product >= MAX_PRODUCT) {
            try {
                System.out.println("产品已满，请稍后再生产");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        this.product++;
        System.out.println("生产者生产第" + this.product + "个产品.");
        notify();
    }

    /**
     * 消费产品
     */
    public synchronized void getProduct() {
        while (product <= MIN_PRODUCT) {
            try {
                System.out.println("缺货，请稍后再取货");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("消费者取走了第" + this.product + "个产品.");
        this.product--;
        notify();
    }
}

class Producer implements Runnable {

    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
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
            clerk.addProduct();
        }
    }

}

class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
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
            clerk.getProduct();
        }
    }

}
