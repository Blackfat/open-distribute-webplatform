package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc   位图
 * @create 2019/1/9-15:11
 */
public class BitMap {

    private char[] bytes;

    private int nbits;

    public BitMap(int nbits){
        this.nbits = nbits;
        bytes = new char[nbits / 8 + 1];
    }

    public void set(int k){
       if(k > nbits){
           return;
       }
       int byteIndex = k / 8;
       int bitIndex = k % 8;
       bytes[byteIndex] |= (1 << bitIndex);

    }

    public boolean get(int k){
        if(k > nbits){
            return false;
        }
        int byteIndex = k / 8;
        int bitIndex = k % 8;

        return (bytes[byteIndex] & (1 << bitIndex)) != 0;
    }







}
