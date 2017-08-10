package com.blackfat.common.designmode.strategy;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/9-14:04
 */
public class ChildrenDiscount implements  Discount {

    @Override
    public double calculate(double price) {
        System.out.println("儿童票：");
        return price - 10;
    }
}
