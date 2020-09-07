package com.blackfat.common.jvm;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-06-22 19:27
 * @since 1.0-SNAPSHOT
 */
public class InitializationTest {

    public static void main(String[] args) {
        //A.print();
        //System.out.println(A.lock);
        System.out.println(A.str);
    }

}

class A {
    public static final Object lock = new Object();

    public static  String str = "test";

    static {
        System.out.println("A static init");
    }

    public A() {
        System.out.println("A init");
    }


    public static void print(){
        System.out.println("A.print");
    }

}