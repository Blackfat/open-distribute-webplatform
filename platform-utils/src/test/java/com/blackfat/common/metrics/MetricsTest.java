package com.blackfat.common.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-02-24 09:05
 * @since 1.0-SNAPSHOT
 */
public class MetricsTest {



    @Test
    public void SimpleMeterRegistryTest(){
        MeterRegistry registry = new SimpleMeterRegistry();
        Counter counter = registry.counter("counter");
        counter.increment();
        System.out.println(counter.measure());
    }



}
