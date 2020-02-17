package com.blackfat.common.utils.core.registry;

import com.blackfat.common.utils.core.registry.node.Node;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-08 16:17
 * @since 1.0-SNAPSHOT
 */
public interface Registry {

    boolean register(Node node);

    boolean unregister(Node node);

    boolean update(Node node);

    void subscribe(String path, NotifyListener notifyListener);


}
