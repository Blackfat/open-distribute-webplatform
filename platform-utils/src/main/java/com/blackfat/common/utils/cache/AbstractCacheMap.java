package com.blackfat.common.utils.cache;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wangfeiyang
 * @desc 缓存默认实现
 * @create 2017/8/8-9:17
 */
public abstract class AbstractCacheMap<K, V> implements Cache<K, V> {

    class CacheObject<K2, V2> {
        final K2 key;
        final V2 cachedObject;
        long lastAccess;        // 最后访问时间
        long accessCount;       // 访问次数
        long ttl;               // 对象存活时间(time-to-live)

        CacheObject(K2 key, V2 value, long ttl) {
            this.key = key;
            this.cachedObject = value;
            this.ttl = ttl;
            this.lastAccess = System.currentTimeMillis();
        }

        boolean isExpired() {
            if (ttl == 0) {
                return false;
            }
            return lastAccess + ttl < System.currentTimeMillis();
        }

        V2 getObject() {
            lastAccess = System.currentTimeMillis();
            accessCount++;
            return cachedObject;
        }
    }

    protected Map<K, CacheObject<K, V>> cacheMap;

    //普通互斥锁
    private final ReentrantLock cacheLock = new ReentrantLock();


    protected int cacheSize;      // 缓存大小 , 0 -> 无限制

    protected boolean existCustomExpire; //是否设置默认过期时间

    protected long defaultExpire;     // 默认过期时间, 0 -> 永不过期

    public int getCacheSize() {
        return cacheSize;
    }

    public AbstractCacheMap(int cacheSize, long defaultExpire) {
        this.cacheSize = cacheSize;
        this.defaultExpire = defaultExpire;
    }

    public boolean isNeedClearExpiredObject() {
        return defaultExpire > 0 || existCustomExpire;
    }


    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public long getDefaultExpire() {
        return defaultExpire;
    }

    @Override
    public void put(K key, V value) {
        put(key, value, defaultExpire);
    }

    @Override
    public void put(K key, V value, long expire) {
        cacheLock.lock();

        try {
            CacheObject<K, V> co = new CacheObject<K, V>(key, value, expire);
            if (expire != 0) {
                existCustomExpire = true;
            }
            if (isFull()) {
                eliminate();
            }
            cacheMap.put(key, co);

        } finally {
            cacheLock.unlock();
        }

    }

    @Override
    public V get(K key) {
        cacheLock.lock();

        try {
            CacheObject<K, V> co = cacheMap.get(key);
            if (co == null) {
                return null;
            }
            if (co.isExpired() == true) {
                cacheMap.remove(key);
                return null;
            }

            return co.getObject();
        } finally {
            cacheLock.unlock();
        }
    }

    /**
     * 淘汰对象具体实现
     *
     * @return
     */
    protected abstract int eliminateCache();


    @Override
    public int eliminate() {
        cacheLock.lock();
        try {
            return eliminateCache();
        } finally {
            cacheLock.unlock();
        }
    }

    @Override
    public void remove(K key) {
        cacheLock.lock();
        try {
            cacheMap.remove(key);
        } finally {
            cacheLock.unlock();
        }
    }

    @Override
    public boolean isFull() {
        if (cacheSize == 0) {//o -> 无限制
            return false;
        }
        return cacheMap.size() >= cacheSize;
    }

    @Override
    public void clear() {
        cacheLock.lock();
        try {
            cacheMap.clear();
        } finally {
            cacheLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
