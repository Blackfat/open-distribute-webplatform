package com.blackfat.common.jvm;

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

     -XX:+UseParNewGC 设置年轻代为多线程收集，可与CMS收集同时使用

     -XX:+HeapDumpOnOutOfMemoryError 出现 OOME 时生成堆 dump

     -XX:HeapDumpPath 生成堆文件地址

     -Xloggc:  生成GC日志地址

     -XX:+PrintHeapAtGC：在GC的时候打印堆的信息

     -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=60  CMS在对内存占用率达到60%的时候开始GC

     --XX:+AlwaysPreTouch 系统启动时，内存就分配给JVM，而不是等JVM访问时才分配

     -XX:PretenureSizeThreshold=2M 分配的对象超过设定值时不在Eden区分配，直接在Old区分配，但是这个参数只能CMS前提下才生效，ParallelGC不生效
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
