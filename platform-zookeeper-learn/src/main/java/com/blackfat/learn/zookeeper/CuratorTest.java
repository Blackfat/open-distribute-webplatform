package com.blackfat.learn.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/10/16-9:33
 */
public class CuratorTest {

    /*
    * connectString：zk的server地址，多个server之间使用英文逗号分隔开
    * connectionTimeoutMs：连接超时时间，如上是30s，默认是15s
    * sessionTimeoutMs：会话超时时间，如上是50s，默认是60s
    * retryPolicy：失败重试策略 baseSleepTimeMs：初始的sleep时间，用于计算之后的每次重试的sleep时间 maxRetries：最大重试次数
    * */
    public static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("101.132.177.27:2181").sessionTimeoutMs(50000)
            .connectionTimeoutMs(30000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();


    public void start() throws Exception {

        /**
         * 创建会话
         */
        client.start();

        /**
         * 1 除非指明创建节点的类型,默认是持久节点
         * 2 ZooKeeper规定:所有非叶子节点都是持久节点,所以递归创建出来的节点,只有最后的数据节点才是指定类型的节点,其父节点是持久节点
         */
        client.create().forPath("/China");
        client.create().forPath("/America", "james".getBytes());
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/France");//创建一个初始内容为空的临时节点
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/Russia/car", "haha".getBytes());//递归创建,/Russia是持久节点




    }

    public static void main(String[] args) throws Exception {
        client.start();

        client.create().forPath("/China","ni hao".getBytes());

        client.setData().forPath("/China", "hello".getBytes());

         client.checkExists().forPath("/China");

    }
}
