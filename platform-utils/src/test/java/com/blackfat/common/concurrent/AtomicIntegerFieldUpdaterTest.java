package com.blackfat.common.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/9/17-11:51
 */
public class AtomicIntegerFieldUpdaterTest {

    public static AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    static class User{
        private String username;

        volatile  int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final User user = new User("james", 1);
        final CountDownLatch countDownLatch = new CountDownLatch(10);

        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        updater.incrementAndGet(user);
                    countDownLatch.countDown();
                };
            }.start();
        }

        countDownLatch.await();
        System.out.println(user.age);


    }
}
