package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/11/30-14:19
 */
public class Heap {
    private int[] a; // 数组，从下标 1 开始存储数据
    private int n;  // 堆可以存储的最大数据个数
    private int count; // 堆中已经存储的数据个数

    public Heap(int capacity) {
        a = new int[capacity + 1];
        n = capacity;
        count = 0;
    }

    public void insert(int data) {
        if (count >= n) return; // 堆满了
        ++count;
        a[count] = data;
        int i = count;
        while (i/2 > 0 && a[i] > a[i/2]) { // 自下往上堆化
            swap(a, i, i/2);
            i = i/2;
        }
    }

    /**
     *  swap() 函数作用：交换下标为 newIndex 和 oldIndex的两个元素
     * @param arr
     * @param newIndex
     * @param oldIndex
     */
    private void swap(int[] arr, int newIndex , int oldIndex){
        int temp = arr[newIndex];
        arr[newIndex] = arr[oldIndex];
        arr[oldIndex] = temp;

    }

    public void removeMax() {
        if (count == 0) return; // 堆中没有数据
        a[1] = a[count];   // 最后一个元素置换到堆顶
        --count;
        heapify(a, count, 1);
    }

    /**
     * 堆化
     * @param a
     * @param n
     * @param i
     */
    private void heapify(int[] a, int n, int i) { // 自上往下堆化
        while (true) {
            int maxPos = i;
            if (i*2 <= n && a[i] < a[i*2]) maxPos = i*2;
            if (i*2+1 <= n && a[maxPos] < a[i*2+1]) maxPos = i*2+1;
            if (maxPos == i) break;
            swap(a, i, maxPos);
            i = maxPos;
        }
    }

}

