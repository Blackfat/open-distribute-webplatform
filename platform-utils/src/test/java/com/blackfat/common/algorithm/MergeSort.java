package com.blackfat.common.algorithm;

import java.util.Arrays;

/**
 * @author wangfeiyang
 * @desc 归并排序
 * @create 2018/10/19-14:02
 */
public class MergeSort {

    static int number=0;

    public static void  sort(int[] arr, int left ,int right){
        if (arr == null || arr.length == 0) {
            return;
        }
        if(left >= right){
            return ;
        }
        int middle = (left + right)/2;

        sort(arr, left,middle);
        sort(arr, middle+1, right);


        merge(arr, left, middle, right);


    }

    public static void merge(int[] a, int left, int mid, int right){
        int[] tmp = new int[a.length];
        int r1 = mid + 1;
        int tIndex = left;
        int cIndex=left;
        // 逐个归并
        while(left <=mid && r1 <= right) {
            if (a[left] <= a[r1])
                tmp[tIndex++] = a[left++];
            else
                tmp[tIndex++] = a[r1++];
        }
        // 将左边剩余的归并
        while (left <=mid) {
            tmp[tIndex++] = a[left++];
        }
        // 将右边剩余的归并
        while ( r1 <= right ) {
            tmp[tIndex++] = a[r1++];
        }


        System.out.println("第"+(++number)+"趟排序:\t");
        //从临时数组拷贝到原数组
        while(cIndex<=right){
            a[cIndex]=tmp[cIndex];
            //输出中间归并排序结果
            System.out.print(a[cIndex]+"\t");
            cIndex++;
        }

        System.out.println();

    }

    public static void main(String[] args) {
        int[] array = new int[]{5, 8, 6, 3, 9, 2, 1, 7};

        sort(array,0, array.length -1);

        System.out.println(Arrays.toString(array));
    }

}
