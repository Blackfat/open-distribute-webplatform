package com.blackfat.common.algorithm;

import java.util.List;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-11-11 09:57
 * @since 1.0-SNAPSHOT
 */
public class DynamicPlanning {


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

}




