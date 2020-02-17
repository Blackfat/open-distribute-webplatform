package com.blackfat.common.utils.core.registry.zookeeper;

import com.alibaba.fastjson.JSONObject;
import com.blackfat.common.utils.core.registry.AbstractRegistry;
import com.blackfat.common.utils.core.registry.NotifyEvent;
import com.blackfat.common.utils.core.registry.node.Node;
import com.blackfat.common.utils.zkclient.ReinforceCuratorFramework;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-08 19:04
 * @since 1.0-SNAPSHOT
 */
public class ZookeeperRegistry extends AbstractRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);
    private static final String PATH_SPERATOR = "/";
    private ReinforceCuratorFramework zkClient;

    public ZookeeperRegistry(ReinforceCuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    protected void doSubscribe(String path) {
        try{
            if(!zkClient.isExist(path)){
                zkClient.createPath(path, (new JSONObject()).toJSONString().getBytes());
            }
            zkClient.watchTreeNodes(path, new TreeCacheListener(){
                @Override
                public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
                    if(event.getData() == null){
                        logger.info("TreeCacheEvent data is empty, event skipped");
                    }else{
                        String childPath = event.getData().getPath();
                        String data = event.getData().getData() == null ? "{}" : new String(event.getData().getData());
                        NotifyEvent.NotifyType notifyType = ZookeeperEventType.toNotifyType(event.getType());
                        if (notifyType == null) {
                            logger.warn("TreeCacheEvent [{}] listener not configured", event.getType());
                        }else  {
                            NotifyEvent notifyEvent = new NotifyEvent(notifyType, childPath, data);
                            doNotify(getRootPath(childPath), notifyEvent);
                        }
                    }
                }
            });
        }catch (Exception e){
            logger.error("Failed to watch zookeeper tree path " + path, e);
        }
    }

    public List<String> getChildren(java.lang.String path) throws Exception {
        if (!StringUtils.isEmpty(path) && zkClient.isExist(path)) {
            GetChildrenBuilder builder = zkClient.getChildren();
            return (java.util.List)builder.forPath(path);
        } else {
            return null;
        }
    }

    public void deletePath(String path) throws Exception {
        zkClient.deletePath(path);
    }

    public boolean isExits(String path) throws Exception {
        return zkClient.isExist(path);
    }

    public String getData(String path) throws Exception {
        return zkClient.isExist(path) ? new String(this.zkClient.getData(path)) : "";
    }

    private String getRootPath(String childPath) {
        int index = childPath.indexOf(PATH_SPERATOR, 1);
        return index > 0 ? childPath.substring(0, index) : childPath;
    }

    @Override
    public boolean register(Node node) {
        boolean succeed = false;
        try{
            if (node != null) {
                zkClient.createPath(node.getPath(), node.getData().toJSONString().getBytes());
                logger.info("register node,path={},data={}", node.getPath(), node.getData().toJSONString());
                succeed = true;
            }
        }catch (Exception e){
            logger.error("register node error,path={},error={}", node.getPath(), e);
        }
        return succeed;
    }

    @Override
    public boolean unregister(Node node) {
        boolean succeed = false;

        try {
            if (node != null) {
                zkClient.deletePath(node.getPath());
                logger.info("delete node ,path={}", node.getPath());
                succeed = true;
            }
        } catch (Exception var4) {
            logger.error("delete node error,path={},error={}", node.getPath(), var4);
        }

        return succeed;
    }

    @Override
    public boolean update(Node node) {
        try{
            if (node == null || node.getType() == null) {
                return false;
            }

            if (zkClient.isExist(node.getPath())) {
                zkClient.putData(node.getPath(), node.getData().toJSONString().getBytes());
                return true;
            }

            logger.warn("zk no this path{} for update", node.getPath());

        }catch (Exception e){
            logger.error("zk update node error,path={},error={}", node.getPath(), e);
        }
        return false;
    }
}
