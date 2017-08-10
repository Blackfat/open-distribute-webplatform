package com.blackfat.common.designmode.strategy;

import ch.qos.logback.core.joran.spi.XMLUtil;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/9-14:00
 */
public class MovieTicket {

    private double price;
    private Discount discount;


    public double getPrice() {
        discount.calculate(price);
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }


    public static void main(String[] args) {
        MovieTicket mt = new MovieTicket();
        double originalPrice = 60.0;
        double currentPrice;

        mt.setPrice(originalPrice);
        System.out.println("原始价为：" + originalPrice);
        System.out.println("---------------------------------");

        Discount discount = new VIPDiscount();
        mt.setDiscount(discount); //注入折扣对象


        currentPrice = mt.getPrice();
        System.out.println("折后价为：" + currentPrice);
    }
}
