package com.blackfat.common.eventBus;

import com.google.common.eventbus.Subscribe;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-03-13 11:04
 * @since 1.0-SNAPSHOT
 */
public class Observer {

    @Subscribe
    public void observer1(String event){
        System.out.println("observer1:"+event);
    }


    @Subscribe
    public void observer2(String event){
        System.out.println("observer2:"+event);
    }
}
