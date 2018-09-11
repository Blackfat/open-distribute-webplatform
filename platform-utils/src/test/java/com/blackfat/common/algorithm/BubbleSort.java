package com.blackfat.common.algorithm;

import java.util.Arrays;

/**
 * @author wangfeiyang
 * @desc 冒泡排序
 * @create 2018/9/11-13:38
 */
public class BubbleSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {

            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }

            }
        }


    }

    public static void sortOpt(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            // 判断是否已经有序
             boolean isSort = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSort = false;
                }
            }

            if(isSort){
                break;
            }

        }


    }

    public static void sortOpt2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        // 最后一次交换的位置
        int lastExchangeIndex = 0;
        //无序数列的边界，每次比较只需要比到这里为止
        int sortBorder = arr.length -1;
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            // 判断是否已经有序
            boolean isSort = true;
            for (int j = 0; j < sortBorder; j++) {

                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSort = false;
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if(isSort){
                break;
            }

        }


    }

    public static void sortOpt3(int[] arr){
        if (arr == null || arr.length == 0) {
            return;
        }
        int temp = 0;
        for( int i = 0 ; i< arr.length/2 ; i ++){
            // 判断是否已经有序
            boolean isSort = true;

            // 从左到右交换
            for(int j = i ; j < arr.length -1 -i;j++ ){
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSort = false;
                }
            }

            if(isSort){
                break;
            }

            // 从右到左交换
            isSort = true;
            for(int j = arr.length -1 -i ; j > i; j-- ){
                 if(arr[j] < arr[j-1]){
                     temp = arr[j];
                     arr[j] = arr[j-1];
                     arr[j-1] = temp;
                     isSort = false;
                 }
            }

            if(isSort){
                break;
            }

        }

    }

    public static void main(String[] args) {
        int[] array = new int[]{5, 8, 6, 3, 9, 2, 1, 7};

//        sort(array);
//
//        System.out.println(Arrays.toString(array));
//
//
//        sortOpt(array);
//
//        System.out.println(Arrays.toString(array));

//        sortOpt2(array);
//
//        System.out.println(Arrays.toString(array));

        sortOpt3(array);

        System.out.println(Arrays.toString(array));
    }
}
