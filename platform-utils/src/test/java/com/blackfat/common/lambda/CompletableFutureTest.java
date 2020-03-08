package com.blackfat.common.lambda;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-03-03 16:40
 * @since 1.0-SNAPSHOT
 */
public class CompletableFutureTest {


    public double getPrice(String product) {


        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        Random random = new Random();

        delay();

        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Future<Double> getPriceAsyn1(String product){
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(()->{
            double price = calculatePrice(product);
            future.complete(price);
        }).start();
        return  future;
    }

    public Future<Double> getPriceAsyn(String product){
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(()->calculatePrice(product));
        return  future;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutureTest completableFutureTest = new CompletableFutureTest();
        Future<Double> price = completableFutureTest.getPriceAsyn("TEST");
        Future<Double> price1 = completableFutureTest.getPriceAsyn1("TEST");
        System.out.println(price.get());
        System.out.println(price1.get());

    }
}
