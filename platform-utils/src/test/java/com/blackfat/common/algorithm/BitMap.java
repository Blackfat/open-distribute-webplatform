package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc   位图
 * @create 2019/1/9-15:11
 */
public class BitMap {
    // Java中char类型占16bit，也即是2个字节

    private char[] bytes;

    private int nbits;

    public BitMap(int nbits){
        this.nbits = nbits;
        bytes = new char[nbits / 16 + 1];
    }

    public void set(int k){
       if(k > nbits){
           return;
       }
       int byteIndex = k / 16;
       int bitIndex = k % 16;
       // 左移是为了匹配位数，从0开始
       bytes[byteIndex] |= (1 << bitIndex);

    }

    public boolean get(int k){
        if(k > nbits){
            return false;
        }
        int byteIndex = k / 16;
        int bitIndex = k % 16;

        return (bytes[byteIndex] & (1 << bitIndex)) != 0;
    }







}
