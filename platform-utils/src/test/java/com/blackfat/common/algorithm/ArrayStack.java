package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc   基于数组实现栈
 * @create 2018/10/10-14:41
 */
public class ArrayStack {

    private String[] items;
    //栈中元素个数
    private int count;
    //栈的大小
    private int n;


    // 初始化数组，申请一个大小为 n 的数组空间 publ...

    public ArrayStack(int n) {
        this.items = new String[n];
        this.n = n;
        this.count = 0;
    }




    //入栈
    public boolean push(String item){
        if(n == count){
            return false;
        }
        items[count] = item;
        count++;
        return true;
    }



    // 出栈
    public String pop(){
        if(count == 0){
            return null;
        }
        String tmp = items[count -1];
        count --;
        return tmp;
    }



}
