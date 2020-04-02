package com.blackfat.common.eventBus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-03-13 11:07
 * @since 1.0-SNAPSHOT
 */
public class Observed {

    private EventBus eventBus;

    public Observed() {
        //同步阻塞
        this.eventBus = new EventBus();
        // 异步非阻塞
        //this.eventBus = new AsyncEventBus(Executors.newFixedThreadPool(5));
    }

    public void setRegObservers(List<Object> observers) {
        for (Object observer : observers) {
            eventBus.register(observer);
        }
    }

    public void post(String event){
        eventBus.post(event);
    }


    public static void main(String[] args) {
        Observed observed = new Observed();
        observed.setRegObservers(Lists.newArrayList(new Observer()));
        observed.post("event");
    }

}
