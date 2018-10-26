package com.blackfat.open.platform.config;

import com.crossoverjie.distributed.constant.RedisToolsConstant;
import com.crossoverjie.distributed.lock.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/26-9:26
 */
@Configuration
public class RedisLockConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisLockConfig.class);

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public RedisLock build(){
        RedisLock redisLock = new RedisLock.Builder(jedisConnectionFactory, RedisToolsConstant.SINGLE)
                .lockPrefix("lock_").sleepTime(100).build();

        return  redisLock;

    }
}
