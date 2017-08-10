package com.blackfat.common.designmode.strategy;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/9-14:05
 */
public class VIPDiscount implements  Discount {

    @Override
    public double calculate(double price) {
        System.out.println("VIP票：");
        System.out.println("增加积分！");
        return price * 0.5;
    }
}
