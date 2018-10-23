package com.blackfat.api.activity.service;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-13:45
 */
public interface OrderServiceApi {

    /**
     * 创建订单
     * @param sid
     *  库存ID
     * @return
     *  订单ID
     */
    int createWrongOrder(int sid) throws Exception;

}
