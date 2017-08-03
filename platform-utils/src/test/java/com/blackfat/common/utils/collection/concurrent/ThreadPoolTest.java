package com.blackfat.common.utils.collection.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/3-14:03
 */
public class ThreadPoolTest {

    /***
     *    corePoolSize：核心池的大小
     *    maximumPoolSize：线程池最大线程数
     *    keepAliveTime：表示线程没有任务执行时最多保持多久时间会终止,默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用
     *    workQueue：一个阻塞队列，用来存储等待执行的任务
     *   如果当前线程池中的线程数目小于corePoolSize，则每来一个任务，就会创建一个线程去执行这个任务；
     *  如果当前线程池中的线程数目>=corePoolSize，则每来一个任务，会尝试将其添加到任务缓存队列当中，若添加成功，则该任务会等待空闲线程将其取出去执行；若添加失败（一般来说是任务缓存队列已满），则会尝试创建新的线程去执行这个任务；
     *  如果当前线程池中的线程数目达到maximumPoolSize，则会采取任务拒绝策略进行处理；
     * 如果线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止，直至线程池中的线程数目不大于corePoolSize；如果允许为核心池中的线程设置存活时间，那么核心池中的线程空闲时间超过keepAliveTime，线程也会被终止。
     *
     * @param args
     */
    public static void main(String[] args) {

        ThreadFactory threadFactory =new  ThreadFactoryBuilder().setNameFormat("测试自定义线程名称-%d").build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),threadFactory);

        for(int i=0;i<15;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                    executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();


    }

}

class MyTask implements Runnable {
    private int taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"正在执行task "+taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
    }
}
