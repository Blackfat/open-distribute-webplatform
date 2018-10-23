package com.blackfat.service.activity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blackfat.api.activity.service.OrderServiceApi;
import com.blackfat.service.activity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-14:46
 */
@Service
public class OrderServiceApiImpl implements OrderServiceApi {

    @Autowired
    private OrderService orderService;

    @Override
    public int createOptimisticOrder(int sid) throws Exception {
        return orderService.createOptimisticOrder(sid);
    }
}
