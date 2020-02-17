package com.blackfat.common.utils.core.configcache.zk;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.blackfat.common.utils.core.configcache.AbstractConfigCache;
import com.blackfat.common.utils.core.configcache.ConfigDef;
import com.blackfat.common.utils.core.registry.NotifyEvent;
import com.blackfat.common.utils.core.registry.NotifyListener;
import com.blackfat.common.utils.core.registry.RegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-10 15:03
 * @since 1.0-SNAPSHOT
 */
public abstract class ZookeeperBasedConfigCache<T extends ConfigDef> extends AbstractConfigCache {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperBasedConfigCache.class);

    protected RegistryFactory registryFactory;

    private ExecutorService notifyWorker;

    public ZookeeperBasedConfigCache(RegistryFactory registryFactory){
       this.registryFactory = registryFactory;
       this.notifyWorker = new ThreadPoolExecutor(0 , Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
               new SynchronousQueue<Runnable>(),
               new NamedThreadFactory("cache-notify"));
    }

    @Override
    public void afterPropertiesSet() {
        //在容器初始化时先全量加载缓存，然后再依赖zk来更新。避免容器已经启动cache还没有加载完成。
        initCache();
        registryFactory.getRegistry().subscribe(getPath(),new NotifyListener() {
            @Override
            public void notify(NotifyEvent notifyEvent) {
                notifyWorker.execute(()->{
                    // 避免zk同步早于数据库提交
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    fireCacheChanged(notifyEvent);
                });
            }
        });
        logger.info("Cache {} begin to listen notify event ", getPath());
        super.afterPropertiesSet();
    }

    @Override
    public T getCache(String identify) {
        return (T) caches.get(identify);
    }

    protected abstract String getPath();

    protected abstract void fireCacheChanged(NotifyEvent event);

    protected abstract void initCache();
}
