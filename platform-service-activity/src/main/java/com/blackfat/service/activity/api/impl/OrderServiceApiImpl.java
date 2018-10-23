package com.blackfat.service.activity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blackfat.api.activity.service.OrderServiceApi;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-14:46
 */
@Service
public class OrderServiceApiImpl implements OrderServiceApi {

    @Override
    public int createWrongOrder(int sid) throws Exception {
        return 0;
    }
}
