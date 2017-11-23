package com.blackfat.common.concurrent;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/11/23-9:40
 */
public class CyclicBarrierTest {

    @Test
    public void CyclicBarrierTest(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("比赛结束");
            }
        });

        // 依次创建并启动5个worker线程
        for (int i = 0; i < 5; ++i) {
            new Thread(new Task(cyclicBarrier)).start();
        }





    }


}

  class Task implements  Runnable{
      private final  CyclicBarrier cyclicBarrier;
      public Task(CyclicBarrier cyclicBarrier) {
          this.cyclicBarrier = cyclicBarrier;
      }

      @Override
      public void run() {
          System.out.println("到达终点");
          try {
              cyclicBarrier.await();
          } catch (InterruptedException e) {
              e.printStackTrace();
          } catch (BrokenBarrierException e) {
              e.printStackTrace();
          }
      }
  }
