package com.blackfat.common.utils.core.registry.zookeeper;

import com.blackfat.common.utils.core.registry.Registry;
import com.blackfat.common.utils.core.registry.RegistryFactory;
import com.blackfat.common.utils.zkclient.ReinforceCuratorFrameworkFactory;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-08 19:12
 * @since 1.0-SNAPSHOT
 */
public class ZookeeperRegistryFactory implements RegistryFactory {
    private String connectString;

    public ZookeeperRegistryFactory(String connectString) {
        this.connectString = connectString;
    }

    @Override
    public Registry getRegistry() {
        return new ZookeeperRegistry(ReinforceCuratorFrameworkFactory.newClientByConnectString(this.connectString));
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }
}
