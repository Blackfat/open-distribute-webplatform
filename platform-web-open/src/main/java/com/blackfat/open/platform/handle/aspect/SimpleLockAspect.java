package com.blackfat.open.platform.handle.aspect;

import com.crossoverjie.distributed.lock.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/26-9:33
 */
@Aspect
@Component
public class SimpleLockAspect {

    @Autowired
    private RedisLock redisLock;

    @Pointcut(value = "@annotation(com.blackfat.open.platform.handle.aspect.SimpleLock)")
    public void execute(){

    }


    @Around("execute()")
    public Object determine(ProceedingJoinPoint jp) throws Throwable{
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method targetMethod = signature.getMethod();
        if(targetMethod.isAnnotationPresent(SimpleLock.class)){
            SimpleLock simpleLock = targetMethod.getAnnotation(SimpleLock.class);
            Object[] args = jp.getArgs();
            String lockKey;
            Integer expireSeconds = simpleLock.expiredSeconds() < 1 ? 5 : simpleLock.expiredSeconds();
            Integer retryCount = simpleLock.retryCount() < 0 ? 1 : simpleLock.retryCount();
            Integer retryDelay = simpleLock.retryDelaySeconds() < 0 ? 1 : simpleLock.retryDelaySeconds();
            if (args == null || args.length == 0) {
                lockKey = targetMethod.getDeclaringClass().getName() + "." + targetMethod.getName();
            } else {
                lockKey = (String) args[simpleLock.lockKeyParameterIndex()];
            }
            String request = UUID.randomUUID().toString();

            if(retryCount == 0){
                return lockExecute(jp, lockKey, request, expireSeconds);
            }else{
                try{
                    return lockExecute(jp, lockKey, request, expireSeconds);
                }catch (Exception e){
                      while(retryCount -- >0){
                          Thread.currentThread().join(retryDelay * 1000);
                          return lockExecute(jp, lockKey, request, expireSeconds);
                      }
                    throw new Exception("get lock: " + lockKey + " error , please try again later");
                }
            }


        }else{
            return  jp.proceed();
        }
    }

    private Object lockExecute(ProceedingJoinPoint jp, String lockKey, String request,Integer expireSeconds) throws Throwable {
        boolean lockHolder = false;
        try {
             lockHolder = redisLock.tryLock(lockKey,request,expireSeconds);
            if (lockHolder) {
                return jp.proceed();
            } else {
                throw new Exception("get lock: " + lockKey + " error , please try again later");
            }
        } finally {
            if (lockHolder) {
                redisLock.unlock(lockKey, request);
            }
        }
    }




}
