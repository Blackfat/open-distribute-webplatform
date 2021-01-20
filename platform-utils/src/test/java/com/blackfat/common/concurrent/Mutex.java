package com.blackfat.common.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

/**
 * @author wangfeiyang
 * @Description 自定义独占锁
 * @create 2021-01-05 09:44
 * @since 1.0-SNAPSHOT
 */
public class Mutex {


    private static  class Sync extends AbstractQueuedSynchronizer{

        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, 1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if(getState() == 0){
                throw new IllegalMonitorStateException();
            }else{
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
        // 返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition() { return new ConditionObject(); }

    }
    // 将锁的操作代理到Sync中
    private final Sync sync;

    public Mutex() {
        this.sync = new Sync();
    }

    public void lock(){
        sync.acquire(1);
    }

    public boolean tryLock(){
        return sync.tryAcquire(1);
    }

    public void unlock(){
         sync.release(1);
    }

    public boolean isLocked(){
       return sync.isHeldExclusively();
    }


    public Condition newCondition(){
        return sync.newCondition();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }





}
