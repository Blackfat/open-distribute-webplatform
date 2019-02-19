package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc    TopN问题
 * @create 2018/10/16-10:01
 */
public class TopN {

    private int parent(int n){
        return (n-1)/2;
    }

    private int left(int n){
        return 2*n +1;
    }

    private int right(int n){
        return 2*n +2;
    }


    /**
     * 构建堆(最小堆)根结点的键值是所有堆结点键值中最小者，且每个结点的值都比其孩子的值小
     * @param n
     * @param data
     */
    private void buildHeap(int n , int[] data){
        for(int i = 0 ; i< n ; i++){
            int t = i;
            while(t != 0 && data[parent(t)] > data[t]){
                int temp = data[t];
                data[t] = data[parent(t)];
                data[parent(t)] = temp;
                t = parent(t);
            }

        }
    }


    /**
     * 调整data[i]
     * @param i
     * @param n
     * @param data
     */
    private void adjust(int i, int n,  int[] data){
        if(data[i] < data[0]){
            return ;
        }

        // 置换堆顶
        int temp = data[i];
        data[i] = data[0];
        data[0] = temp;
        // 调整堆顶
        int t =0 ;
        while( (left(t) < n && data[t] > data[left(t)])
                || (right(t) < n && data[t] > data[right(t)])){
              // 右孩子更小，置换右孩子
            if(right(t) < n && data[right(t)] < data[left(t)]){
                temp = data[t];
                data[t] = data[right(t)];
                data[right(t)] = temp;
                t = right(t);
            }
            // 否则置换左孩子
            else{
                temp = data[t];
                data[t] = data[left(t)];
                data[left(t)] = temp;
                t = left(t);
            }
        }
    }

    /**
     * 寻找topN，该方法改变data，将topN排到最前面
     * @param n
     * @param data
     */
    public void findTopN( int n , int[] data){

        // 先构建n个数的小顶堆
        buildHeap(n, data);

       print(data);

        // n往后的数进行调整
        for(int i = n; i < data.length; i++) {
            adjust(i, n, data);
        }
    }



    /**
     * 打印数组
     * @param data
     */
    public void print(int[] data){
        for(int i= 0 ;i < data.length ; i++){
            System.out.print(data[i] + ",");
        }
        System.out.println();

    }


    public static void main(String[] args) {
        TopN topN = new TopN();
        int[] arr1 = new int[]{56, 30, 71, 18, 29, 93, 44, 75, 20, 65, 68, 34};

        System.out.println("原数组：");
        topN.print(arr1);
        topN.findTopN(5, arr1);
        System.out.println("调整后数组：");
        topN.print(arr1);


    }







}
