package com.blackfat.open.platform.config;

import com.crossoverjie.distributed.constant.RedisToolsConstant;
import com.crossoverjie.distributed.limit.RedisLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/24-10:00
 */
@Configuration
public class RedisLimitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisLimitConfig.class);

    @Value("${redis.limit}")
    private int limit;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;


    @Bean
    public RedisLimit build(){
        RedisLimit redisLimit = new RedisLimit.Builder(jedisConnectionFactory, RedisToolsConstant.SINGLE)
                .limit(limit)
                .build();

        return redisLimit;


    }



}
