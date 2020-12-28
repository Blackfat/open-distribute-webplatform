package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-11-04 10:00
 * @since 1.0-SNAPSHOT
 */
public class AllSort {
    public static  int count=0;
    public static void fun(int[] arr,int k,int j){
        if(k==j){
            count++;
            System.out.print(count+":");
            print(arr);
        }else{
            for(int i=k;i<=j;i++){
                swap(arr,k,i);
                fun(arr,k+1,j);
                swap(arr,k,i);
            }

        }
    }
    public static void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
    public static void print(int[] arr){
        for(int i:arr){
            System.out.print(i+",");
        }
        System.out.println("");
    }
    public static void main(String[] args) {
        int[] arr=new int[]{1,2,3,4};
        fun(arr,0,arr.length-1);
    }

}
