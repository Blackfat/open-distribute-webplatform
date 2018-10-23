package com.blackfat.service.activity.service;

import com.blackfat.api.activity.entity.Stock;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-15:00
 */
public interface StockService {

    /**
     * 获取剩余库存
     * @param id
     * @return
     */
    int getStockCount(int id) ;

    /**
     * 根据库存 ID 查询库存信息
     * @param id
     * @return
     */
    Stock getStockById(int id) ;


    /**
     * 乐观锁更新库存
     * @param stock
     * @return
     */
    int updateStockByOptimistic(Stock stock);
}
