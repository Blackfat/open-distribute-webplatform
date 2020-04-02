package com.blackfat.common.concurrent;

import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-03-10 08:41
 * @since 1.0-SNAPSHOT
 */
public class ConcurrentHashMapTest
{
    // 循环次数
    private  static int LOOP_COUNT = 10000000;

    // 线程数量
    private static int THREAD_COUNT = 10;

    // 元素数量
    private static int ITEM_COUNT = 1000;


    public static Map normaluse() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(()->{
            IntStream.rangeClosed(1,LOOP_COUNT).parallel().forEach( i ->{
                //获得一个随机的Key
                String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                synchronized (freqs){
                    if(freqs.containsKey(key)){
                        freqs.put(key, freqs.get(key) + 1);
                    }else{
                        freqs.put(key, 1L);
                    }
                }
            });
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return  freqs;
    }


    public static Map gooduse() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(()->{
            IntStream.rangeClosed(1,LOOP_COUNT).parallel().forEach( i ->{
                //获得一个随机的Key
                String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                freqs.computeIfAbsent(key, k -> new LongAdder()).increment();
            });
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return  freqs.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue().longValue())
        );
    }


    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map normaluse = normaluse();
        stopWatch.stop();
        stopWatch.start("gooduse");
        Map gooduse = gooduse();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}
