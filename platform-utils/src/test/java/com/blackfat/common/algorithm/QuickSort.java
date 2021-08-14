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

        int pivotIndex = partition1(arr, startIndex, endIndex);


        // 根据基准元素，分成两部分递归排序
        sort(arr, startIndex, pivotIndex - 1);
        sort(arr, pivotIndex + 1, endIndex);

    }

    /**
     *
     * 2,1,5,4,3
     *
     * @param arr
     * @param startIndex
     * @param endIndex
     * @return
     */
    private static int partition1(int[] arr, int startIndex, int endIndex){
          int pivot = arr[endIndex];
          int i = startIndex;
          for(int j = startIndex; j < endIndex; j++){
              if(arr[j] < pivot){
                 swap(arr, i ,j);
                  i++;
              }

          }
          swap(arr, i , endIndex);
          return  i;
    }

    public static void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
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
        int[] array = new int[]{2,1,5,4,3};

        sort(array,0, array.length -1);

        System.out.println(Arrays.toString(array));
    }

}
