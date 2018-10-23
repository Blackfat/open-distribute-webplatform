package com.blackfat.service.activity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blackfat.api.activity.service.StockServiceApi;
import com.blackfat.service.activity.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-15:04
 */
@Service
public class StockServiceApiImpl implements StockServiceApi {

    @Autowired
    private StockService stockService;

    @Override
    public Integer getCurrentCount() throws Exception {
        return null;
    }
}
