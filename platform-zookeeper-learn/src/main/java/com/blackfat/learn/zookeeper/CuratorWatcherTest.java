package com.blackfat.learn.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/10/18-9:50
 */
public class CuratorWatcherTest {

    /*
    * connectString：zk的server地址，多个server之间使用英文逗号分隔开
    * connectionTimeoutMs：连接超时时间，如上是30s，默认是15s
    * sessionTimeoutMs：会话超时时间，如上是50s，默认是60s
    * retryPolicy：失败重试策略 baseSleepTimeMs：初始的sleep时间，用于计算之后的每次重试的sleep时间 maxRetries：最大重试次数
    * */
    public static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("101.132.177.27:2181").sessionTimeoutMs(50000)
            .connectionTimeoutMs(30000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();


    public static void main(String[] args) throws Exception {
        client.start();
//        client.create().creatingParentsIfNeeded().forPath("/book/computer", "java".getBytes());

        /*
        *  监听指定节点本身的变化,包括节点本身的创建和节点本身数据的变化
        * */
//        final NodeCache nodeCache = new NodeCache(client, "/book/computer");
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("新的节点数据:" + new String(nodeCache.getCurrentData().getData()));
//            }
//        });
//
//        nodeCache.start();
//        // NodeCache太过频繁的触发事件
//        Thread.sleep(1000);
//        client.setData().forPath("/book/computer","c++".getBytes());
//
//        Thread.sleep(1000);
//        client.setData().forPath("/book/computer","python".getBytes());

        final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/book13",true);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()){
                    case CHILD_ADDED:
                        System.out.println("新增子节点:" + pathChildrenCacheEvent.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("删除子节点:" + pathChildrenCacheEvent.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("更新子节点:" + pathChildrenCacheEvent.getData().getPath());
                        break;
                    default:
                        break;

                }

            }
        });

        pathChildrenCache.start();

        client.create().forPath("/book13");

        // zookeeper原生API注册Watcher需要反复注册，即Watcher触发之后就需要重新进行注册,导致了zookeeper客户端不能够接收全部的zookeeper事件
        Thread.sleep(1000);
        client.create().forPath("/book13/car", "bmw".getBytes());

        Thread.sleep(1000);
        client.setData().forPath("/book13/car", "audi".getBytes());

        Thread.sleep(1000);
        client.delete().forPath("/book13/car");



    }
}
