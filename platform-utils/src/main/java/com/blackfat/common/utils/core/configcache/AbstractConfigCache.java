package com.blackfat.common.utils.core.configcache;

import com.alibaba.fastjson.JSONObject;
import com.blackfat.common.utils.core.thread.NameThreadFactory;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-10 13:51
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractConfigCache implements  ConfigCache, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConfigCache.class);

    private static final int DUMP_INIT_DELAY = 30;

    private static final int DUMP_FIX_DELAY = 5 * 60;

    protected Map<String, ConfigDef> caches = Maps.newConcurrentMap();

    private ScheduledExecutorService dumpScheduler = new ScheduledThreadPoolExecutor(1, new NameThreadFactory("cache-dump"));


    @Override
    public void afterPropertiesSet(){
        dumpScheduler.scheduleAtFixedRate(()->{
            logger.info("{} - Cache dumped - {}", getClass().getSimpleName(), JSONObject.toJSONString(caches));
        },DUMP_INIT_DELAY, DUMP_FIX_DELAY, TimeUnit.SECONDS);
    }



}
