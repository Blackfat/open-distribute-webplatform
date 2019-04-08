package com.blackfat.learn.zookeeper;

import com.sun.corba.se.impl.orbutil.ObjectUtility;
import org.I0Itec.zkclient.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.junit.Test;

import java.util.List;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/3/19-13:51
 */
public class zkClientRTest {

    @Test
    public void zkTest1(){
        ZkClient  zkClient = new ZkClient("101.132.177.27:2181",5000);

        String path = "/zKClient";

        if( !zkClient.exists(path)){
            System.out.println("create node"+path);
            zkClient.createPersistent(path);
        }

        zkClient.create(path + "/children","child node", CreateMode.EPHEMERAL);

        List<String> children = zkClient.getChildren(path);

        System.out.println(children.toArray());

        int childCount = zkClient.countChildren(path);


        System.out.println(path+":childCount"+childCount);

        zkClient.writeData(path + "/children" , "hello world");

        Object object = zkClient.readData(path + "/children");

        System.out.println(object);

        zkClient.close();


    }

    @Test
    public void zkClient2() throws InterruptedException{
        ZkClient  zkClient = new ZkClient("101.132.177.27:2181",5000);

        String path = "/zKClient";

        if( !zkClient.exists(path)){
            System.out.println("create node:"+path);
            zkClient.createPersistent(path);
        }

        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.err.println("parentPath: " + parentPath);
                System.err.println("currentChilds: " + currentChilds);
            }
        });

        zkClient.subscribeDataChanges(path + "/children1", new IZkDataListener(){
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.err.println("dataPath change date:"+data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });

        zkClient.subscribeStateChanges(new IZkStateListener(){

            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {

            }

            @Override
            public void handleNewSession() throws Exception {

            }

            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception {

            }
        });


        Thread.sleep(3000);


        zkClient.create(path + "/children1","child1 node", CreateMode.EPHEMERAL);


        Thread.sleep(3000);

        zkClient.writeData(path + "/children1","child1 node change");

//        Thread.sleep(3000);
//
//        zkClient.create(path + "/children2","child2 node", CreateMode.EPHEMERAL);
//
//        Thread.sleep(3000);

        zkClient.close();
    }

    @Test
    public void zkClient3() throws InterruptedException {
        ZkClient  zkc = new ZkClient("101.132.177.27:2181",5000);
        zkc.deleteRecursive("/test");
        //对父节点添加监听子节点变化。
        zkc.subscribeChildChanges("/test", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("parentPath: " + parentPath);
                System.out.println("currentChilds: " + currentChilds);
            }
        });
        Thread.sleep(3000);
        zkc.createPersistent("/test");
        Thread.sleep(5000);
        //parentPath: /test
        //currentChilds: []
        zkc.createPersistent("/test" + "/" + "aa", "aa内容");
        Thread.sleep(5000);
        //parentPath: /test
        //currentChilds: [aa]
        zkc.createPersistent("/test" + "/" + "bb", "bb内容");
        Thread.sleep(5000);
        //parentPath: /test
        //currentChilds: [aa, bb]
        zkc.delete("/test/bb");
        Thread.sleep(5000);
        //parentPath: /test
        //currentChilds: [aa]
        zkc.deleteRecursive("/test");//因为此方法为递归删除，所以触发2次
        //parentPath: /test
        //currentChilds: null //删除后，子节点集合为null
        //parentPath: /test
        //currentChilds: null
        Thread.sleep(Integer.MAX_VALUE);
    }
}
