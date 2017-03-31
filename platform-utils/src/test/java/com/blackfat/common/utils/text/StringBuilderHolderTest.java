package com.blackfat.common.utils.text;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import static org.assertj.core.api.Assertions.*;

public class StringBuilderHolderTest {

    @Test
    public void test() throws InterruptedException {
        /*CountDownLatch 是能使一组线程等另一组线程都跑完了再继续跑*/
        final CountDownLatch countdown = new CountDownLatch(10);
        /*CyclicBarrier 能够使一组线程在一个时间点上达到同步，可以是一起开始执行全部任务或者一部分任务*/
        final CyclicBarrier barrier = new CyclicBarrier(10);

        Runnable runnable = new Runnable() {

            StringBuilderHolder holder = new StringBuilderHolder(512);
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {

                    e.printStackTrace();
                }
                StringBuilder builder = StringBuilderHolder.getGlobal();
                builder.append(Thread.currentThread().getName() + "-1");
                System.out.println(builder.toString());

                builder = StringBuilderHolder.getGlobal();
                builder.append(Thread.currentThread().getName() + "-2");
                System.out.println(builder.toString());

                StringBuilder builder2 = holder.get();
                builder2.append(Thread.currentThread().getName() + "-11");
                System.out.println(builder2.toString());

                builder2 = holder.get();
                builder2.append(Thread.currentThread().getName() + "-22");
                System.out.println(builder2.toString());

                countdown.countDown();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }

        countdown.await();

    }


}