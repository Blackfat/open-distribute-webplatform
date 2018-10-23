package com.blackfat.api.activity.service;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-13:45
 */
public interface StockServiceApi {

    /**
     * 获取当前库存
     * @return
     * @throws Exception
     */
    Integer getCurrentCount() throws Exception;
}
