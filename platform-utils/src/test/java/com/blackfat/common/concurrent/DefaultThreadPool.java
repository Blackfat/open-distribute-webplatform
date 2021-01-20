package com.blackfat.common.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-01-04 19:35
 * @since 1.0-SNAPSHOT
 */
public class DefaultThreadPool <Job extends Runnable>implements ThreadPool<Job> {
    // 线程池的最大的数量
     private static final int    MAX_WORKER_NUMBERS    = 10;
    // 线程池默认的数量
    private static final int    DEFAULT_WORKER_NUMBERS = 5;
    // 线程池最小的数量
    private static final int    MIN_WORKER_NUMBERS    = 1;
    // 这是一个工作列表，将会向里面插入工作
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    // 工作者列表
    private final List<Worker> workers    = Collections.synchronizedList(new
            ArrayList<Worker>());
    // 工作者线程的数量
    private int    workerNum    = DEFAULT_WORKER_NUMBERS;
    // 线程编号生成
    private AtomicInteger threadNum    = new AtomicInteger();
    public DefaultThreadPool() {
        initializeWokers(DEFAULT_WORKER_NUMBERS);
    }

      public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS :num;
        initializeWokers(workerNum);
    }

    // 初始化线程工作者
    private void initializeWokers(int num) {
        for (int i = 0; i < num; i++) {
             Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    // 工作者，负责消费任务
    class Worker implements Runnable {
        // 是否工作
        private volatile boolean    running    = true;
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    // 如果工作者列表是空的，那么就wait
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException ex) {
                            // 感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    // 取出一个Job
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception ex) {
                        // 忽略Job执行中的Exception
                    }
                }
            }
        }
        public void shutdown() {
            running = false;
        }
    }


    @Override
    public void execute(Job job) {
        if(jobs != null){
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for(Worker worker : workers){
            worker.shutdown();
        }
    }

    @Override
    public Integer getJobSize() {
       return jobs.size();
    }
}
