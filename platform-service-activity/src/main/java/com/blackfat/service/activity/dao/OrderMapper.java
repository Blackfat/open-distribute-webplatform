package com.blackfat.service.activity.dao;

import com.blackfat.api.activity.entity.StockOrder;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-14:37
 */
public interface OrderMapper {

    int insertSelective(StockOrder order);
}
