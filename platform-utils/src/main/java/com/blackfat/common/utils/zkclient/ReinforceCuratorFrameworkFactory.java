package com.blackfat.common.utils.zkclient;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReinforceCuratorFrameworkFactory {

    private static Map<String, ReinforceCuratorFramework> cachedCuratorFrameworks = new ConcurrentHashMap();

    public ReinforceCuratorFrameworkFactory() {
    }

    public static ReinforceCuratorFramework newClientByConnectString(String connectString) {
        if (!cachedCuratorFrameworks.containsKey(connectString)) {
            Class var1 = ReinforceCuratorFrameworkFactory.class;
            synchronized(ReinforceCuratorFrameworkFactory.class) {
                if (!cachedCuratorFrameworks.containsKey(connectString)) {
                    CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectString, new RetryOneTime(1));
                    ReinforceCuratorFramework reinforceCuratorFramework = new ReinforceCuratorFramework(curatorFramework);
                    reinforceCuratorFramework.start();
                    cachedCuratorFrameworks.put(connectString, reinforceCuratorFramework);
                }
            }
        }

        return (ReinforceCuratorFramework)cachedCuratorFrameworks.get(connectString);
    }
}
