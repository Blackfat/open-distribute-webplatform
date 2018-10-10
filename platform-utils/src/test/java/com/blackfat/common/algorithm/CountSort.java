package com.blackfat.common.algorithm;

import java.util.Arrays;

/**
 * @author wangfeiyang
 * @desc 计数排序
 * @create 2018/10/10-13:52
 */
public class CountSort {

    public static int[] sort(int[] array){
       // 得到最大值和最小值，并计算差值
        int max = array[0];
        int min = array[0];
        for(int i=1 ; i< array.length ;i++){
            if(array[i] > max){
                max = array[i];
            }
            if(array[i] < min){
                min = array[i];
            }
        }
        int d = max - min;

        // 创建数组并统计对应的元素个数
        int[] countArray = new int[d+1];
        for(int i=0 ; i< array.length ;i++){
            countArray[array[i]- min]++;
        }

        //统计数组变形，后面的元素等于前面的数字之和,变形后的值代表位置顺序
        int sum = 0;
        for(int i=0 ; i< countArray.length ;i++){
            sum+= countArray[i];
            countArray[i]=sum;
        }

        //倒序遍历原始数组，从统计数组中找出正确的位置，输出到结果数组
        int[] sortArray = new int[array.length];
        for(int i=array.length-1 ; i>=0 ;i--){
            sortArray[countArray[array[i]-min]-1] = array[i];
            countArray[array[i] - min]--;
        }

        return sortArray;
    }



    public static void main(String[] args) {
        int[] array = new int[]{95,94,91,94,90};
        int[] sortArray = sort(array);
        System.out.println(Arrays.toString(sortArray));
    }



}
