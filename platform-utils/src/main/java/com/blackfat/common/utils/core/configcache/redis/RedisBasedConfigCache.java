package com.blackfat.common.utils.core.configcache.redis;

import com.blackfat.common.utils.core.configcache.AbstractConfigCache;
import com.blackfat.common.utils.core.configcache.ConfigDef;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-08-31 19:03
 * @since 1.0-SNAPSHOT
 */
public abstract class RedisBasedConfigCache<T extends ConfigDef> extends AbstractConfigCache {



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


    public T getCache(String code){
        // 如果获取失败，或者有版本更新时；
        if(isDirty(code, getDefVersion(code))){
            synchronized (code.intern()){
                long remoteVersion = getDefVersion(code);
                if (isDirty(code, remoteVersion)) {
                    T newDef = getDefDetail(code);
                    if (newDef != null) {
                        caches.put(code, newDef);
                        if (newDef.getDataVersion() > remoteVersion) {
                            // todo redis 写入数据version
                        }
                    }
                }
            }
        }
        return (T) caches.get(code);
    }



}
