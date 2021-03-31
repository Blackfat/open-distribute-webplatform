package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @Description 回溯算法
 * 穷举搜索所有可能的装法，然后找出满足条件的最大值
 * @create 2021-03-28 22:07
 * @since 1.0-SNAPSHOT
 */
public class BackTracking {


    private Integer maxW = Integer.MIN_VALUE; // 结果

    private  static int[] weight = {2,2,4,6,3}; // 物品重量

    private  int n = 5; // 物品个数

    private  int w = 9; // 背包承受的最大重量

    private boolean[][] mem = new boolean[5][10]; // 备忘录，默认值false

    // i:待装入的物品 cw:背包的重量
    public void f(int i, int cw){
        if(cw == w || i == n){
            if(cw > maxW){
                maxW = cw;
            }
            return;
        }
        if (mem[i][cw]) return; // 重复状态
         mem[i][cw] = true; // 记录(i, cw)这个状态
        f(i+1,cw);// 不装i+1个物品
        if(cw + weight[i] <= w){
            f(i+1,cw + weight[i]); // 选择装第i个物品
        }

    }




    /**
     * 动态规则
     * 问题分解为多个阶段，每个阶段对应一个决策。
     * 我们记录每一个阶段可达的状态集合（去掉重复的），
     * 然后通过当前阶段的状态集合，
     * 来推导下一个阶段的状态集合，
     * 动态地往前推进
     * @param weight
     * @param n
     * @param w
     * @return
     */
    //weight:物品重量，n:物品个数，w:背包可承载重量
    public static int knapsack(int[] weight, int n, int w) {
        boolean[][] states = new boolean[n][w+1]; // 默认值false
        states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        if (weight[0] <= w) {
            states[0][weight[0]] = true;
        }
        for (int i = 1; i < n; ++i) { // 动态规划状态转移
            for (int j = 0; j <= w; ++j) {// 不把第i个物品放入背包
                if (states[i-1][j] == true) states[i][j] = states[i-1][j];
            }
            for (int j = 0; j <= w-weight[i]; ++j) {//把第i个物品放入背包
                if (states[i-1][j]==true) states[i][j+weight[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n-1][i] == true) return i;
        }
        return 0;
    }


    public static int knapsack2(int[] items, int n, int w) {
        boolean[] states = new boolean[w+1]; // 默认值false
        states[0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        if (items[0] <= w) {
            states[items[0]] = true;
        }
        for (int i = 1; i < n; ++i) { // 动态规划
            // 如果将第i个物品放入背包，我们需要在当前背包总重量的所有取值中，找到小于等于j的（j=w-items[i]）
            for (int j = w-items[i]; j >= 0; --j) {//把第i个物品放入背包
                if (states[j]==true) states[j+items[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[i] == true) return i;
        }
        return 0;
    }



    public static void main(String[] args) {
        BackTracking backTracking = new BackTracking();
        backTracking.f(0,0);
        System.out.println(backTracking.maxW);
        System.out.println(knapsack(weight,5, 9));
        System.out.println(knapsack2(weight,5, 9));

    }




}
