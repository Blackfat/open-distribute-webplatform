package com.blackfat.common.utils.core.configcache;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-10 13:50
 * @since 1.0-SNAPSHOT
 */
public interface ConfigCache {

    ConfigDef getCache(String identify);
}
