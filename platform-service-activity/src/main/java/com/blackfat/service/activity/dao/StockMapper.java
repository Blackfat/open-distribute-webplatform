package com.blackfat.service.activity.dao;

import com.blackfat.api.activity.entity.Stock;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-14:37
 */
public interface StockMapper {

    Stock selectByPrimaryKey(Integer  id);


    int updateByOptimistic(Stock stock);


}
