package com.blackfat.common.utils.core.configcache.redis;

import com.blackfat.common.utils.core.configcache.AbstractConfigCache;
import com.blackfat.common.utils.core.configcache.ConfigDef;
import com.blackfat.common.utils.core.thread.NameThreadFactory;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-08-31 19:20
 * @since 1.0-SNAPSHOT
 */
public abstract class RedisBasedScheduledConfigCache <T extends ConfigDef> extends AbstractConfigCache implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RedisBasedScheduledConfigCache.class);

    private ScheduledExecutorService updateScheduler = new ScheduledThreadPoolExecutor(1, new NameThreadFactory("update-cache"));
    /**
     * 定时任务机制,单位ms
     */
    private long updateInitDelay = 5;
    private long updateFixDelay = 5;

    private boolean isDirty(String code, Long remoteVersion) {
        return remoteVersion == 0L || !caches.containsKey(code) || remoteVersion > caches.get(code).getDataVersion();
    }

    protected Long getDefVersion(String code) {
        // todo 从redis中获取数据version
        String version = "";
        return StringUtils.isEmpty(version) ? 0L : Long.parseLong(version);
    }

    protected abstract String getRediskey(String code);

    protected abstract T getDefDetail(String code);

    protected abstract Map<String, T> initCache();


    @Override
    public void afterPropertiesSet() {
        Map<String, T> cacheMap = initCache();
        if (cacheMap != null) {
            caches.putAll(cacheMap);
        }
        super.afterPropertiesSet();
    }







    @Override
    public ConfigDef getCache(String code) {
        /**
         * 1：数据最终一致
         * 2：只管拿；防止是错误code的，导致数据库的压力；
         */
        if (caches.containsKey(code)) {
            return (T) caches.get(code);
        } else {
            /**
             * 对外的接口防止错误的code把自己搞死,如果code已开on的时候，才会主动加载一次；
             */
            if (getDefVersion(code) > 0) {
                tryUpdate(code);
                return (T) caches.get(code);
            }
            return null;
        }
    }

    private void tryUpdate(String code) {
        // 如果获取失败，或者有版本更新时；
        if (isDirty(code, getDefVersion(code))) {
            logger.info("{} code {} is dirty",getClass().getSimpleName(),code);
            // 同步操作；
            synchronized (code.intern()) {
                long remoteVersion = getDefVersion(code);
                if (isDirty(code, remoteVersion)) {
                    T newDef = getDefDetail(code);
                    if (newDef != null) {
                        caches.put(code, newDef);
                        logger.info("{} code {} update to version {},old version {}",getClass().getSimpleName(),code,newDef.getDataVersion(),remoteVersion);
                        if (newDef.getDataVersion() > remoteVersion) {
                            // todo redis 写入数据version

                        }
                    }else {
                        logger.info("{} code {} not exist, ignore update",getClass().getSimpleName(),code);
                    }
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.updateScheduler.scheduleWithFixedDelay(() -> {
            if (MapUtils.isNotEmpty(caches)) {
                caches.forEach((code, value) -> {
                    try {
                        tryUpdate(code);
                    } catch (Exception e) {
                        logger.error("{} schedule update error {}",getClass().getSimpleName(),e);
                    }
                });
            }

        }, updateInitDelay, updateFixDelay, TimeUnit.SECONDS);
    }
}
