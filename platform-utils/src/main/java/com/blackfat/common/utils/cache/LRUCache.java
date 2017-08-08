package com.blackfat.common.utils.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangfeiyang
 * @desc LRU(Least Recently Used ，最近最少使用)
 * @create 2017/8/8-10:24
 */
public class LRUCache<K, V> extends  AbstractCacheMap<K, V> {

    @SuppressWarnings("serial")
    public LRUCache(int cacheSize, long defaultExpire) {
        super(cacheSize, defaultExpire);


        // linkedHash已经实现LRU算法 是通过双向链表来实现，当某个位置被命中，通过调整链表的指向将该位置调整到头位置，新加入的内容直接放在链表头，如此一来，最近被命中的内容就向链表头移动，需要替换时，链表最后的位置就是最近最少使用的位置
        // 排序模式accessOrder，该属性为boolean型变量，对于访问顺序，为true；对于插入顺序，则为false
        // linkedHashMap  accessOrder 为true  get操作会改变数据链表ReentrantReadWriteLock读写互斥时，并发get会出错
        this.cacheMap = new LinkedHashMap<K, CacheObject<K, V>>(cacheSize + 1, 1f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheObject<K, V>> eldest) {

                return LRUCache.this.removeEldestEntry(eldest);
            }

        };
    }

    private boolean removeEldestEntry(Map.Entry<K, CacheObject<K, V>> eldest) {

        if (cacheSize == 0)
            return false;

        return size() > cacheSize;
    }


    @Override
    protected int eliminateCache() {
        if (!isNeedClearExpiredObject()) {
            return 0;
        }

        Iterator<CacheObject<K, V>> iterator = cacheMap.values().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            CacheObject<K, V> cacheObject = iterator.next();

            if (cacheObject.isExpired()) {
                iterator.remove();
                count++;
            }
        }

        return count;
    }
}
