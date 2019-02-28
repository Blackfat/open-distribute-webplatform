package com.blackfat.common.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author wangfeiyang
 * @desc
 * @create 2019/2/28-9:49
 */


/**
 * benchmark 结果所使用的时间单位
 */
@OutputTimeUnit(TimeUnit.MICROSECONDS)
/**
 * Throughput: 整体吞吐量，例如“1秒内可以执行多少次调用”。
 * AverageTime: 调用的平均时间，例如“每次调用平均耗时xxx毫秒”
 * SampleTime: 随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”
 * SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能
 */
@BenchmarkMode(Mode.Throughput)
public class SimpleBenchmark {

    private static AtomicLong count = new AtomicLong();

    private static LongAdder longAdder = new LongAdder();


    @Benchmark
    @Threads(10)
    public void run0() throws InterruptedException {
        count.getAndIncrement();
    }


    @Benchmark
    @Threads(10)
    public void run1() throws InterruptedException {
        longAdder.increment();
    }


    public static void main(String[] args) throws RunnerException {
        // forks(1)运行环境复制一份
        Options options = new OptionsBuilder().include(SimpleBenchmark.class.getName()).forks(1).build();
        new Runner(options).run();


    }


}
