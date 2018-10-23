package com.blackfat.service.activity.service;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-14:59
 */
public interface OrderService {

    /**
     * 创建订单 乐观锁
     * @param sid
     * @return
     * @throws Exception
     */
    int createOptimisticOrder(int sid) throws Exception;
}
