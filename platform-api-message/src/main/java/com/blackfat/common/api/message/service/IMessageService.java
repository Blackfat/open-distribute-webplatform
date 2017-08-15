package com.blackfat.common.api.message.service;

import com.blackfat.common.api.message.entity.MessageInfo;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/15-11:10
 */
public interface IMessageService {

    /**
     * 发送信息
     *
     * @param messageInfo
     * @return
     * @throws Exception
     */
    public int sendMessage(MessageInfo messageInfo) throws Exception;


}
