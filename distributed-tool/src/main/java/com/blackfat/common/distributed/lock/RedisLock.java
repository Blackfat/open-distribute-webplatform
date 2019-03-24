package com.blackfat.common.distributed.lock;

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
public class RedisLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedisLock(Builder builder){
        this.jedisConnectionFactory = builder.jedisConnectionFactory;
        this.lockPrefix = builder.lockPrefix;
        this.type = builder.type;
        buildScript();
    }

    private void buildScript(){
        script = ScriptUtil.getScript("lock.lua");
    }

    private static final String LOCK_MSG = "OK";

    private static final Long UNLOCK_MSG = 1L;

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private String lockPrefix;

    private JedisConnectionFactory jedisConnectionFactory;

    private int type ;

    private String script;

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


    /**
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean tryLock(String key, String value, int expireTime){

        Object connection = getConnection();
        String result ;

        if (connection instanceof Jedis){
            result = ((Jedis) connection).set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            ((Jedis) connection).close();
        }else {
            result = ((JedisCluster) connection).set(lockPrefix + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            try {
                ((JedisCluster) connection).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (LOCK_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key, String value){
        // 获取连接
       Object connection = getConnection();

       Object result = null;

       if(connection instanceof Jedis){
            result = ((Jedis)connection).eval(script, Collections.singletonList(lockPrefix+key),
                    Collections.singletonList(value));
            ((Jedis) connection).close();
       }else{
           result = ((JedisCluster)connection).eval(script, Collections.singletonList(lockPrefix+key),
                   Collections.singletonList(value));
       }

        if (UNLOCK_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    public static class Builder{

        //默认锁的前缀
        private final static String DEFAULT_LOCK_PREFIX = "lock_";

        private JedisConnectionFactory jedisConnectionFactory = null;

        private int type;

        private String lockPrefix = DEFAULT_LOCK_PREFIX;

        public Builder(JedisConnectionFactory jedisConnectionFactory, int type){
            this.jedisConnectionFactory = jedisConnectionFactory;
            this.type = type;
        }

        public Builder lockPrefix(String lockPrefix){
            this.lockPrefix = lockPrefix;
            return this;
        }

        public RedisLock build(){
            return new RedisLock(this);
        }








    }
}
