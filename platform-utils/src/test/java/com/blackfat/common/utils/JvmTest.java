package com.blackfat.common.utils;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/7/21-11:20
 */
public class JvmTest {


    /*
    *
    * -Xms:指定JVM初始化内存

     -Xmx:指定JVM堆的最大内存，在JVM启动以后，会分配-Xmx参数指定大小的内存给JVM，但是不一定全部使用，JVM会根据-Xms参数来调节真正用于JVM的内存

     -Xmn：参数设置了新生代的大小；老年代等于-Xmx减去-Xmn

     -XX:+PrintGCApplicationStoppedTime :记录程序运行期间所有情况引发的STW

     -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1:记录STW发生的原因、线程情况、STW各个阶段的停顿时间

     -XX:+PrintGCDetails详细的GC信息

     -XX:+PrintGCTimeStamps 打印GC的时间戳

     -XX:+PrintTenuringDistribution  幸存者区（Survivors）对象的年龄分布（age distribution）

     -XX:+UseSerialGC 使用串行GC

     -XX:+UseParallelGC 使用并行GC

     -XX:ParallelGCThreads=NNN 来指定 GC 线程数

     -XX:+UseConcMarkSweepGC 使用CMS

    *
    * */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            byte[] bytes = new byte[10*1024 * 1024];
            Thread.sleep(1000);
            System.out.println("------JvmTest-------");
            System.gc();

        }

    }

}
