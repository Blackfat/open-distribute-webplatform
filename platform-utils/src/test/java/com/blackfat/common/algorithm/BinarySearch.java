package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc   二分查找
 * @create 2018/10/29-10:39
 */
public class BinarySearch {

    /**
     * 查找第一个值等于给定值的元素
     * @param a   有序数组
     * @param n
     * @param value
     * @return
     */
    public int bsearch1(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid =  low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == 0) || (a[mid - 1] != value)) return mid;
                else high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个值等于给定值的元素
     * @param a   有序数组
     * @param n
     * @param value
     * @return
     */
    public int bsearch2(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid =  low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == n - 1) || (a[mid + 1] != value)) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }


    /**
     * 查找第一个大于指定值的元素
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int bsearch3(int[] a, int n, int value){
        int low = 0;
        int high = n - 1;
        while (low <= high){
            int  mid = low + ((low + high) >> 1);
            if(a[mid] >= value){
                if(mid == 0 || a[mid -1] < value ){
                    return  mid;
                }else high = mid -1;
            }else{
                low = mid +1;
            }
        }
        return -1;
    }


    /**
     *  查找最后一个小于等于给定值的元素
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int bsearch4(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid =  low + ((high - low) >> 1);
            if (a[mid] > value) {
                high = mid - 1;
            } else {
                if ((mid == n - 1) || (a[mid + 1] > value)) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }



}
