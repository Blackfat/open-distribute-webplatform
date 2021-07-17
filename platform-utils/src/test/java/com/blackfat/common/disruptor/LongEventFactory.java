package com.blackfat.common.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-05-14 17:25
 * @since 1.0-SNAPSHOT
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
