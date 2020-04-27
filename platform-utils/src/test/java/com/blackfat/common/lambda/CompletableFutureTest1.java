package com.blackfat.common.lambda;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-22 13:38
 * @since 1.0-SNAPSHOT
 */
public class CompletableFutureTest1 {

    @Test
    public void futureTest() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1 finished!");
            return "future1 finished!";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future2 finished!");
            return "future2 finished!";
        });
        CompletableFuture<Void> combindFuture = CompletableFuture.allOf(future1, future2);
        try {
            combindFuture.get(2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("future1: " + future1.isDone() + " future2: " + future2.isDone());
    }


    @Test
    public void futureTest1() throws Exception {
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(()->{
            int i = 1/0;
            return 100;
        });
        future2.get();
        future2.join();
    }

    @Test
    public void futureTest2()throws Exception{
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        Future<Integer> f = future3.whenComplete((v, e) -> {
            System.out.println(v);
            System.out.println(e);
        });
        System.out.println(f.get());
    }
}
