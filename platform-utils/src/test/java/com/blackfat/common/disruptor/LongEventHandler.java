package com.blackfat.common.disruptor;


import com.lmax.disruptor.EventHandler;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-05-14 17:27
 * @since 1.0-SNAPSHOT
 */
public class LongEventHandler implements EventHandler<LongEvent> {


    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event: " + event);
    }
}
