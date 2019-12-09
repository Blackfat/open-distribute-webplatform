package com.blackfat.common.utils.zkclient;

import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.Map;
import java.util.concurrent.TimeUnit;


public class ReinforceCuratorFramework {

    private CuratorFramework client;
    private Map<String, InterProcessLock> lockMap = Maps.newConcurrentMap();
    private static final int DEFAULT_WAITING_TIME = 1;

    public ReinforceCuratorFramework(CuratorFramework client) {
        this.client = client;
    }

    public void start() {
        this.client.start();
    }

    public boolean createPath(String path, byte[] data) throws Exception {
        if (!this.isExist(path)) {
            return this.client.create().creatingParentsIfNeeded().forPath(path, data) != null;
        } else {
            return false;
        }
    }

    public void deletePath(String path) throws Exception {
        if (this.isExist(path)) {
            this.client.delete().deletingChildrenIfNeeded().forPath(path);
        }

    }

    public boolean putData(String path, byte[] data) throws Exception {
        if (this.isExist(path)) {
            return this.client.setData().forPath(path, data) != null;
        } else {
            return false;
        }
    }

    public byte[] getData(String path) throws Exception {
        return this.isExist(path) ? (byte[])this.client.getData().forPath(path) : null;
    }

    public GetChildrenBuilder getChildren() {
        return this.client.getChildren();
    }

    public void watchTreeNodes(String path, TreeCacheListener listener) throws Exception {
        TreeCache cache = null;

        try {
            if (this.isExist(path)) {
                cache = new TreeCache(this.client, path);
                cache.getListenable().addListener(listener);
                cache.start();
            }

        } catch (Exception var5) {
            if (cache != null) {
                cache.close();
            }

            throw var5;
        }
    }

    public boolean isExist(String path) throws Exception {
        return this.client.checkExists().forPath(path) != null;
    }

    private InterProcessLock acquireLock(String lockPath) {
        if (!this.lockMap.containsKey(lockPath)) {
            synchronized(lockPath.intern()) {
                if (!this.lockMap.containsKey(lockPath)) {
                    this.lockMap.put(lockPath, new InterProcessMutex(this.client, lockPath));
                }
            }
        }

        return (InterProcessLock)this.lockMap.get(lockPath);
    }

    public boolean tryLock(String lockPath) throws Exception {
        return this.tryLock(lockPath, TimeUnit.SECONDS.toMillis(1L));
    }

    public boolean tryLock(String lockPath, long timeoutMilliSeconds) throws Exception {
        InterProcessLock lock = this.acquireLock(lockPath);
        boolean locked = lock.acquire(timeoutMilliSeconds, TimeUnit.MILLISECONDS);
        return locked;
    }

    public void releaseLock(String lockPath) throws Exception {
        InterProcessMutex lock = (InterProcessMutex)this.lockMap.get(lockPath);
        if (lock != null && lock.isAcquiredInThisProcess()) {
            lock.release();
        }

    }
}
