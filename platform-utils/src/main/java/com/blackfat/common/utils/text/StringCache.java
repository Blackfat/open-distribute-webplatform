package com.blackfat.common.utils.text;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  * An alternative to {@link String#intern()} with no capacity constraints.
 *    copy from com.netflix.discovery.util.StringCache
 */
public class StringCache {

    public static final int LENGTH_LIMIT = 38;

    private static final StringCache INSTANCE = new StringCache();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<String, WeakReference<String>> cache = new WeakHashMap<String, WeakReference<String>>();
    private final int lengthLimit;

    public StringCache() {
        this(LENGTH_LIMIT);
    }

    public StringCache(int lengthLimit) {
        this.lengthLimit = lengthLimit;
    }

    public String cachedValueOf(final String str) {
        if (str != null && (lengthLimit < 0 || str.length() <= lengthLimit)) {
            // Return value from cache if available
            try {
                lock.readLock().lock();
                WeakReference<String> ref = cache.get(str);
                if (ref != null) {
                    return ref.get();
                }
            } finally {
                lock.readLock().unlock();
            }

            // Update cache with new content
            try {
                lock.writeLock().lock();
                WeakReference<String> ref = cache.get(str);
                if (ref != null) {
                    return ref.get();
                }
                cache.put(str, new WeakReference<>(str));
            } finally {
                lock.writeLock().unlock();
            }
            return str;
        }
        return str;
    }

    public int size() {
        try {
            lock.readLock().lock();
            return cache.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static String intern(String original) {
        return INSTANCE.cachedValueOf(original);
    }
}
