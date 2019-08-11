package com.blackfat.common.utils.bean;

import java.util.function.Supplier;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-08-05 16:40
 * @since 1.0-SNAPSHOT
 */
public class ThreadBeanHolder<T> {

   private  Supplier<T> supplier;

   private  final ThreadLocal<T> threadLocal = new ThreadLocal<>();

   public ThreadBeanHolder(Supplier<T> supplier){
       this.supplier = supplier;
   }

   public T get(){
       T t = threadLocal.get();
       if(t != null){
           return t;
       }
       t = supplier.get();
       threadLocal.set(t);
       return  t;
   }
}
