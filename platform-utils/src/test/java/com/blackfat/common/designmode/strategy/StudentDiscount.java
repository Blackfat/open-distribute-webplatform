package com.blackfat.common.designmode.strategy;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/9-14:04
 */
public class StudentDiscount implements Discount {

    @Override
    public double calculate(double price) {
        System.out.println("学生票：");
        return price * 0.8;
    }
}
