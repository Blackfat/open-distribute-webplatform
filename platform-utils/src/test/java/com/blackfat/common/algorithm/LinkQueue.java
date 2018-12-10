package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/12/10-14:06
 */
public class LinkQueue {

    public static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }
    }

    // 队头
    private Node head;

    // 队尾
    private Node tail;

    // 元素个数
    private int size;


    public LinkQueue(){
        head = null;
        tail =null;
        size = 0;
    }

    public void enQueue(int data){
        Node node = new Node(data);
        if(head == null){
            head = node;
            tail = head;
        }else{
            tail.next = node;
            tail = node;
        }
        size++;
    }


    public int deQueue(){
        if(head  == null) {
            return head.getData();
        }
        else {
            Integer data = head.data;
            Node fNode = head;
            head = fNode.next;
            //将头节点指向null，等待垃圾回收器回收
            fNode = null;
            size--;
            return data;
        }
    }

    public boolean isEmpty(){
        if(head == null && tail == null){
            return true;
        }else{
            return false;
        }
    }

    public int size(){
        return this.size;
    }

    public static void main(String[] args) {
        LinkQueue linkQueue = new LinkQueue();

        linkQueue.enQueue(5);
        linkQueue.enQueue(4);
        linkQueue.enQueue(3);
        linkQueue.enQueue(2);
        linkQueue.enQueue(1);
        System.out.println(linkQueue.size());

        System.out.println(linkQueue.deQueue());
        System.out.println(linkQueue.deQueue());
        System.out.println(linkQueue.deQueue());
        System.out.println(linkQueue.deQueue());
        System.out.println(linkQueue.deQueue());

        System.out.println(linkQueue.size());
    }



}
