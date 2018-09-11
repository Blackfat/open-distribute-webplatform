package com.blackfat.common.algorithm;

import java.util.Arrays;

/**
 * @author wangfeiyang
 * @desc 快速排序
 * @create 2018/9/11-15:25
 */
public class QuickSort {

    public static void sort(int[] arr, int startIndex, int endIndex) {
        if (arr == null || arr.length == 0) {
            return;
        }

        if (startIndex >= endIndex) {
            return;
        }

        int pivotIndex = partition(arr, startIndex, endIndex);


        // 根据基准元素，分成两部分递归排序
        sort(arr, startIndex, pivotIndex - 1);
        sort(arr, pivotIndex + 1, endIndex);

    }

    private static int partition(int[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];

        int left = startIndex;

        int right = endIndex;

        while(left != right){
            //控制right指针比较并左移
            while (left<right && arr[right] > pivot){
                right--;
            }
            //控制right指针比较并右移
            while( left<right && arr[left] <= pivot) {
                left++;
            }

            if(left < right){
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }

        }

        //pivot和指针重合点交换
        int p = arr[left];
        arr[left] = arr[startIndex];
        arr[startIndex] = p;

        return left;
    }


    public static void main(String[] args) {
        int[] array = new int[]{5, 8, 6, 3, 9, 2, 1, 7};

        sort(array,0, array.length -1);

        System.out.println(Arrays.toString(array));
    }

}
