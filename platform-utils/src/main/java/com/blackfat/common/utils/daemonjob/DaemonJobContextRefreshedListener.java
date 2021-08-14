package com.blackfat.common.utils.daemonjob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:42
 * @since 1.0-SNAPSHOT
 */
@Component
public class DaemonJobContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    DaemonJobProcessor daemonJobProcessor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        daemonJobProcessor.doOnStartedEvent();
    }
}
