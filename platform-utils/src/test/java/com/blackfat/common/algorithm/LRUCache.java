package com.blackfat.common.algorithm;

import java.util.*;

public class LRUCache {
    //节点类
    class Node{
        int key;
        int val;
        Node next;
        Node pre;
        Node(int key,int val){
            this.key=key;
            this.val=val;
        }
    }

    //双向链表类
    class DoubleList{
        Node head=new Node(0,0);
        Node tail=new Node(0,0);
        int size;

        DoubleList(){
            head.next=tail;
            tail.pre=head;
            this.size=0;
        }

        //添加到链表首部
        public void addFirst(Node node){
            Node headNext=head.next;
            head.next=node;
            node.pre=head;
            node.next=headNext;
            headNext.pre=node;
            size++;
        }

        //删除节点
        public void remove(Node node){
            node.pre.next=node.next;
            node.next.pre=node.pre;
            size--;
        }

        //删除最后一个节点,并返回该节点
        public Node removeLast(){
            Node last=tail.pre;
            remove(last);
            return last;
        }

        //获取链表大小
        public int size(){
            return size;
        }

    }

    private Map<Integer,Node> map;

    private DoubleList cache;

    private int capacity;

    LRUCache(int capacity){
        map=new HashMap<>();
        cache=new DoubleList();
        this.capacity=capacity;
    }

    public int get(int key){
        if(!map.containsKey(key)){
            return -1;
        }
        int val = map.get(key).val;
        //put操作使之放在队首
        put(key,val);
        return val;
    }

    public void put(int key,int value){
        Node node=new Node(key,value);
        if(map.containsKey(key)){
            cache.remove(map.get(key));
        }else{
            if(cache.size()==capacity){
                Node last = cache.removeLast();
                map.remove(last.key);
            }
        }
        cache.addFirst(node);
        map.put(key,node);
    }

    public int[] LRU (int[][] operators, int k) {
        // write code here
        LRUCache cache = new LRUCache(k);
        List<Integer> list=new ArrayList<>();
        for(int[] operator: operators){
            int key=operator[1];
            switch(operator[0]){
                case 1 ://put
                    int value=operator[2];
                    cache.put(key,value);
                    break;
                case 2 ://get
                    int val=cache.get(key);
                    list.add(val);
                    break;
            }
        }
        int[] res=new int[list.size()];
        for(int i=0;i<list.size();i++){
            res[i]=list.get(i);
        }
        return res;
    }
}

