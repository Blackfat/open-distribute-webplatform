package com.blackfat.common.concurrent;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author wangfeiyang
 * @desc
 * @create 2019/4/8-9:28
 */
public class AtomicStampedReferenceTest {

    public static final AtomicStampedReference<Integer> reference =
            new AtomicStampedReference<>(10, 0);


    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            final int version = reference.getStamp();
            new Thread(() ->{
                Integer  integer = reference.getReference();
                if(reference.compareAndSet(integer,integer + 10, version, version+1)){
                    System.out.println(Thread.currentThread().getName()+"--------更新成功！"+reference.getReference());
                }else{
                    System.out.println(Thread.currentThread().getName()+"--------更新失败!"+reference.getReference());
                }
            }).start();
        }
    }
}
