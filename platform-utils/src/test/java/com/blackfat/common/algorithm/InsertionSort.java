package com.blackfat.common.algorithm;

import java.util.Arrays;

/**
 * @author wangfeiyang
 * @desc 插入排序
 * @create 2018/10/15-10:32
 */
public class InsertionSort {


    public static void sort(int[] arr){
        if (arr == null || arr.length == 0) {
            return;
        }

        for(int i=1 ; i< arr.length; i++ ){
            int value = arr[i];
            int j ;
            for( j = i -1;  j>=0 ; j--){
                if(arr[j] > value){
                    arr[j+1] = arr[j];
                }
                else{
                    break;
                }

            }

            arr[j+1] = value;
        }

    }


    public static void main(String[] args) {
        int[] array = new int[]{5, 8, 6, 3, 9, 2, 1, 7};

        sort(array);

        System.out.println(Arrays.toString(array));
    }

}
