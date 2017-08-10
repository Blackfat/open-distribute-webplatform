package com.blackfat.common.designmode.observer;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/9-14:34
 */
public interface Observer {

    String getName();

    void setName(String name);

    void help();

    void beAttacked(AllyControlCenter acc);


}
