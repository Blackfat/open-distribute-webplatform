package com.blackfat.common.utils.core.registry.zookeeper;

import com.blackfat.common.utils.core.registry.NotifyEvent.NotifyType;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-08 17:25
 * @since 1.0-SNAPSHOT
 */
public enum ZookeeperEventType {

    NODE_ADDED(TreeCacheEvent.Type.NODE_ADDED, NotifyType.NODE_ADDED),
    NODE_REMOVED(TreeCacheEvent.Type.NODE_REMOVED, NotifyType.NODE_REMOVED),
    NODE_UPDATED(TreeCacheEvent.Type.NODE_UPDATED, NotifyType.NODE_UPDATED);

    private NotifyType notifyType;
    private TreeCacheEvent.Type zookeeperType;

    private ZookeeperEventType(TreeCacheEvent.Type zookeeperType, NotifyType notifyType) {
        this.zookeeperType = zookeeperType;
        this.notifyType = notifyType;
    }

    public static NotifyType toNotifyType(TreeCacheEvent.Type zookeeperType) {
        ZookeeperEventType[] types = values();
        int length = types.length;

        for(int  i= 0;  i < length; ++i) {
            ZookeeperEventType eventType = types[i];
            if (eventType.zookeeperType == zookeeperType) {
                return eventType.notifyType;
            }
        }

        return null;
    }
}
