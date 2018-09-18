package com.blackfat.common.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangfeiyang
 * @desc 奇偶数打印1-100
 * @create 2018/9/18-9:30
 */
public class Odevity {

    private static final Lock lock = new ReentrantLock();
    /**
     * 奇偶性 默认奇数  通过lock保证可见性
     */
    private  boolean flag = false;


    private int start = 1;

    /**
     * 最大值
     */
    private int max;

    public Odevity(int max){
        this.max = max;
    }


    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    /**
     * 偶数打印线程
     */
    public static class evenNum implements Runnable{

        private Odevity odevity;

        public evenNum(Odevity odevity) {
            this.odevity = odevity;
        }

        @Override
        public void run() {
            while(odevity.start <= odevity.max){
                if(odevity.flag){
                    try{
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + "+-+" + odevity.start);
                        odevity.start++;
                        odevity.flag = false;
                    }catch (Exception e){

                    }finally {
                        lock.unlock();
                    }
                }else {
                    try {
                        //防止线程空转
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


    /**
     * 基数打印线程
     */
    public static class unevenNum implements Runnable{

        private Odevity odevity;

        public unevenNum(Odevity odevity) {
            this.odevity = odevity;
        }

        @Override
        public void run() {
            while(odevity.start <= odevity.max){
                if(!odevity.flag){
                    try{
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + "+-+" + odevity.start);
                        odevity.start++;
                        odevity.flag = true;
                    }catch (Exception e){

                    }finally {
                        lock.unlock();
                    }
                }else {
                    try {
                        //防止线程空转
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public static void main(String[] args) {
        Odevity odevity = new Odevity(100);


        Thread  enven = new Thread(new evenNum(odevity));
        enven.setName("even");

        Thread unenven = new Thread(new unevenNum(odevity));
        unenven.setName("unenven");

        enven.start();
        unenven.start();





    }
}
