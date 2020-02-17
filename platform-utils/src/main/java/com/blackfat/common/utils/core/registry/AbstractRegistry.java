package com.blackfat.common.utils.core.registry;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-08 16:39
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractRegistry implements Registry {

    private static final Logger logger = LoggerFactory.getLogger(AbstractRegistry.class);

    private final ConcurrentMap<String, List<NotifyListener>> notifyListeners = new ConcurrentHashMap();

    public AbstractRegistry() {
    }

    @Override
    public void subscribe(String path, NotifyListener listener) {
        this.addNotifyListener(path, listener);
        this.doSubscribe(path);
        logger.info("registry {} start to listener", path);
    }

    private void addNotifyListener(String path , NotifyListener notifyListener){
        if(notifyListener != null){
           List<NotifyListener>  listeners = (List)this.notifyListeners.get(path);
           if(listeners == null){
               notifyListeners.putIfAbsent(path, new CopyOnWriteArrayList<>());
               listeners = this.notifyListeners.get(path);
           }

            if (!listeners.contains(notifyListener)) {
                listeners.add(notifyListener);
            }

        }
    }

    public void doNotify(String path, NotifyEvent event) {
        List<NotifyListener> listeners = (List)this.notifyListeners.get(path);
        if (CollectionUtils.isNotEmpty(listeners)) {
            Iterator iterator = listeners.iterator();

            while(iterator.hasNext()) {
                NotifyListener listener = (NotifyListener)iterator.next();
                listener.notify(event);
            }
        } else {
            logger.error("registry {} has no listener", path);
        }

    }

    protected abstract void doSubscribe(String path);



}
