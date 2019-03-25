package com.blackfat.common.distributed.handler.aspect;

import com.blackfat.common.distributed.limit.RedisLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author wangfeiyang
 * @desc
 * @create 2019/3/25-13:43
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CommonAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(CommonAspect.class);

    @Autowired
    private RedisLimit redisLimit;

    @Pointcut(value="@annotation(com.blackfat.common.distributed.annotion.CommonLimit)")
    public void execute(){

    }

    @Before("execute()")
    public void doBefore(JoinPoint jp){

        if (redisLimit == null) {
            throw new NullPointerException("redisLimit is null");
        }

        boolean limit = redisLimit.limit();
        if (!limit) {
            logger.warn("request has bean limited");
            throw new RuntimeException("request has bean limited") ;
        }

    }

}
