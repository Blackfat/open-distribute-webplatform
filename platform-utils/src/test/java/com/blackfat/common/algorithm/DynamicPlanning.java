package com.blackfat.common.algorithm;

import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-11-11 09:57
 * @since 1.0-SNAPSHOT
 */
public class DynamicPlanning {


    /**
     * 凑齐问题 回溯解法
     * coins 中是可选硬币面值，amount 是目标金额
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange(List<Integer> coins, int amount) {
        if (amount == 0) return 0;
        int ans = Integer.MAX_VALUE;
        for (Integer coin : coins) {
            if (amount - coin < 0) continue;
            int subProb = coinChange(coins, amount - coin);
            if (subProb == -1) {
                continue;
            }
            ans = Math.min(ans, subProb + 1);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static void main(String[] args) {
        System.out.println(coinChange(Lists.newArrayList(1,2,5), 11));
        System.out.println(coinChange1(Lists.newArrayList(1,2,5), 11));
    }


    /**
     * 凑齐问题 动态规划解法 自底向上
     * coins 中是可选硬币面值，amount 是目标金额
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange1(List<Integer> coins, int amount){
        if (amount == 0) return 0;
        // 当目标金额为i时，至少需要x枚硬币
        int[] dp = new int[amount+1];
        for(int i= 0 ; i< dp.length ; i++){
            dp[i] = amount + 1;
        }
        dp[0] = 0;
        for(int i= 0 ; i< dp.length ; i++){
            for(Integer coin : coins){
                // 内层 for 在求所有子问题 + 1 的最小值
                if(i- coin < 0) continue;
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return  (dp[amount] == amount + 1) ? -1 : dp[amount];
    }

    private static int  minDist = Integer.MAX_VALUE;
    /**
     *n 乘以 n 的矩阵 w[n][n]的最短路径问题 回溯解法 自顶向下
     * @param i
     * @param j
     * @param dist
     * @param w
     * @param n
     * @return
     */
    public static void minDist(int i, int j, int dist, int[][]w, int n){
         if( i == n && j== n){
            if(dist < minDist){
                minDist = dist;
            }
            return ;
         }
         if(i < n){
             minDist(i+1, j, dist+w[i][j], w, n);
         }
         if(j < n){
             minDist(i, j+1, dist+w[i][j], w, n);
         }
    }


    public int minDistDP(int[][] matrix, int n) {
        int[][] states = new int[n][n];
        int sum = 0;
        for (int j = 0; j < n; ++j) { // 初始化states的第一行数据
            sum += matrix[0][j];
            states[0][j] = sum;
        }
        sum = 0;
        for (int i = 0; i < n; ++i) { // 初始化states的第一列数据
            sum += matrix[i][0];
            states[i][0] = sum;
        }
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < n; ++j) {
                states[i][j] =
                        matrix[i][j] + Math.min(states[i][j-1], states[i-1][j]);
            }
        }
        return states[n-1][n-1];
    }






}




