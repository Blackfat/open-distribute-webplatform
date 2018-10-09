package com.blackfat.common.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangfeiyang
 * @desc   让JVM先进行3次YGC,再进行一次CMSGC
 * @create 2018/10/9-9:36
 */
public class CmsGcTest {

    /*
    * -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails
    * -Xmx40m -Xms40m -Xmn10m
    * -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
    * -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=60  CMS在对内存占用率达到60%的时候开始GC
    * */
    public static void main(String[] args) throws InterruptedException {

        // list集合全局引入byte数组, 是为了每次YGC后，byte[]不被回收，直接进入Old区
        List<byte[]> holdList = new ArrayList<>();

        // 由于main方法允许肯定会有1~2M内存，所以为了触发第一次YGC，这里只需要分配7M即可
        for (int i=0; i<7; i++){
            holdList.add(new byte[1*1024*1024]);
        }

        // 为了触发第2,3次YGC，每次也只需要分配7M
        for (int i=0; i<7; i++){
            holdList.add(new byte[1*1024*1024]);
        }
        for (int i=0; i<7; i++){
            holdList.add(new byte[1*1024*1024]);
        }

        // sleep一下子为了让CMS GC线程能够有足够的时间检测到Old区达到了触发CMS GC的条件，
        // CMS GC线程默认2s扫描一次，可以通过参数CMSWaitDuration配置，例如-XX:CMSWaitDuration=3000
        Thread.sleep(1000);


    }
}
