package com.blackfat.common.distributed.limit;

import com.blackfat.common.distributed.constant.Constant;
import com.blackfat.common.distributed.util.ScriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Collections;

/**
 * @author blackfat
 * @create 2019-03-24-下午4:04
 */
public class RedisLimit {

    private static final Logger logger = LoggerFactory.getLogger(RedisLimit.class);

    private RedisLimit(Builder builder){
        this.limit = builder.limit ;
        this.jedisConnectionFactory = builder.jedisConnectionFactory;
        this.type = builder.type ;
        buildScript();
    }

    private String script;

    private JedisConnectionFactory jedisConnectionFactory;
    private int type ;
    private int limit = 200;

    private static final int FAIL_CODE = 0;

    private void buildScript(){
        script = ScriptUtil.getScript("limit.lua");
    }

    public static class Builder{

        private int limit = 200;

        private JedisConnectionFactory jedisConnectionFactory = null;

        private int type;

        public Builder(JedisConnectionFactory jedisConnectionFactory, int type){
            this.jedisConnectionFactory = jedisConnectionFactory;
            this.type = type;
        }

        public Builder limit(int limit){
            this.limit = limit;
            return this;
        }

        public RedisLimit build(){
            return new RedisLimit(this);
        }
    }

    /**
     * get Redis connection
     * @return
     */
    private Object getConnection() {
        Object connection ;
        if (type == Constant.SINGLE){
            RedisConnection redisConnection = jedisConnectionFactory.getConnection();
            connection = redisConnection.getNativeConnection();
        }else {
            RedisClusterConnection clusterConnection = jedisConnectionFactory.getClusterConnection();
            connection = clusterConnection.getNativeConnection() ;
        }
        return connection;
    }

    public boolean limit(){
        Object connection = getConnection();

        Object result = limitRequest(connection);

        if (FAIL_CODE != (Long) result) {
            return true;
        } else {
            return false;
        }
    }

    private Object limitRequest(Object connection) {
        Object result = null;
        String key = String.valueOf(System.currentTimeMillis() / 1000);
        if (connection instanceof Jedis){
            result = ((Jedis)connection).eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limit)));
            ((Jedis) connection).close();
        }else {
            result = ((JedisCluster) connection).eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limit)));
        }
        return result;
    }

}
