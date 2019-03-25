package com.blackfat.common.distributed.handler.aspect;

import com.blackfat.common.distributed.annotion.SimpleLock;
import com.blackfat.common.distributed.lock.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/11/23-9:56
 */
@Aspect
@Component
public class SimpleLockAspect {

    @Autowired
    private RedisLock redisLock;

    @Pointcut(value = "@annotation(com.blackfat.common.distributed.annotion.SimpleLock)")
    public void execute(){

    }

    @Around("execute()")
    public Object determine(ProceedingJoinPoint jp)throws Throwable{
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method targetMethod = signature.getMethod();
        if(targetMethod.isAnnotationPresent(SimpleLock.class)){
            SimpleLock simpleLock = targetMethod.getAnnotation(SimpleLock.class);
            String lockKey = simpleLock.lockKey();
            Integer expireSeconds = simpleLock.expiredSeconds() < 1 ? 10 : simpleLock.expiredSeconds();
            Integer retryCount = simpleLock.retryCount() < 0 ? 1 : simpleLock.retryCount();
            Integer retryDelay = simpleLock.retryDelaySeconds() < 0 ? 1 : simpleLock.retryDelaySeconds();
            if(StringUtils.isEmpty(lockKey)){
                lockKey = targetMethod.getDeclaringClass().getName()+":"+targetMethod.getName();
            }
            if(retryCount == 0){
                return lockExecute(jp, lockKey, expireSeconds);
            }else{
                try{
                    return lockExecute(jp, lockKey, expireSeconds);
                }catch (Exception e){
                    while (retryCount -- > 0){
                        Thread.currentThread().join(retryDelay * 1000);
                        return lockExecute(jp, lockKey, expireSeconds);
                    }
                    throw new Exception("get lock: " + lockKey + " error , please try again later");
                }
            }
        }else{
            return jp.proceed();
        }
    }

    private Object lockExecute(ProceedingJoinPoint jp, String lockKey, Integer expireSeconds) throws Throwable {
        boolean lockHolder = false;
        try {
            lockHolder = redisLock.tryLock(lockKey,lockKey,expireSeconds);
            if (lockHolder) {
                return jp.proceed();
            } else {
                throw new Exception("get lock: " + lockKey + " error , please try again later");
            }
        } finally {
            if (lockHolder) {
                redisLock.unlock(lockKey,lockKey);
            }
        }
    }

}
