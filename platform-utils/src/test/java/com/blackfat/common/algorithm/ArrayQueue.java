package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc 基于数组实现队列
 * @create 2018/10/10-14:42
 */
public class ArrayQueue {

    private String[] items;
    private int n= 0;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int capacity){
        this.items = new String[capacity];
        this.n= capacity;
    }

    public boolean enqueue(String item){
        if(tail == n){
           if(head == 0){
               return false;
           }
           for(int i=head; i<tail; i++){
               items[i-head] = items[i];
           }
           tail = tail -head;
            head = 0;
        }
        items[tail] = item;
        tail++;
        return true;
    }

    public String dequeue(){
        if(head == tail){
            return null;
        }
        String ret = items[head];
        head++;
        return ret;

    }

}
