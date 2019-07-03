package com.blackfat.common.utils.serialization;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-03 21:30
 * @since 1.0-SNAPSHOT
 */
public class SerializationPool {


    private static volatile SerializationPool POOL_FACTORY;

    private GenericObjectPool<Serialization> serializationPool;

    private SerializationPool() {
    }

    private static SerializationPool getInstance(GenericObjectPool<Serialization> serializationPool) {
        if (POOL_FACTORY == null) {
            synchronized (SerializationPool.class) {
                if (POOL_FACTORY == null) {
                    POOL_FACTORY = new SerializationPool();
                    POOL_FACTORY.serializationPool = serializationPool;
                }
            }
        }
        return POOL_FACTORY;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Serialization borrow() {
        try {
            return getSerializationPool().borrowObject();
        } catch (final Exception ex) {
            return null;
        }
    }

    public void restore(final Serialization object) {
        getSerializationPool().returnObject(object);
    }

    private GenericObjectPool<Serialization> getSerializationPool() {
        return serializationPool;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static class Builder {

        private int maxTotal = 30;
        private int minIdle = 10;
        private long maxWaitMillis = 50L;
        private long minEvictableIdleTimeMillis = 2000L;
        private PooledObjectFactory<Serialization> pooledObjectFactory;

        public Builder maxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder minIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }

        public Builder maxWaitMillis(long maxWaitMillis) {
            this.maxWaitMillis = maxWaitMillis;
            return this;
        }

        public Builder minEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
            return this;
        }

        public Builder pooledObjectFactory(PooledObjectFactory<Serialization> pooledObjectFactory) {
            this.pooledObjectFactory = pooledObjectFactory;
            return this;
        }

        public SerializationPool build() {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(maxTotal);
            config.setMinIdle(minIdle);
            config.setMaxWaitMillis(maxWaitMillis);
            config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            GenericObjectPool<Serialization> serializationPool = new GenericObjectPool<>(pooledObjectFactory);
            serializationPool.setConfig(config);
            return getInstance(serializationPool);
        }

    }
}
