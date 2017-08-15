package com.blackfat.common.service.message.service.impl;

import com.blackfat.common.api.message.entity.MessageInfo;
import com.blackfat.common.api.message.service.IMessageService;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/15-11:24
 */
@Service
public class MessageServiceImpl implements IMessageService {

    private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);


    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public int sendMessage( final MessageInfo messageInfo) throws Exception {

        if(messageInfo == null){
            throw new Exception("消息不能为空");
        }

        if(StringUtils.isEmpty(messageInfo.getConsumerQueue())){
            throw new Exception("消息队列不能为空");
        }

        messageInfo.setCreateTime(new Date());
        // 默认状态
        messageInfo.setStatus(0);
        messageInfo.setIsDead(0);
        // 默认发送次数
        messageInfo.setSendTimes(0);

        // 发送消息
        jmsTemplate.setDefaultDestinationName(messageInfo.getConsumerQueue());
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(messageInfo.getMessageBody());
            }
        });
        return 0;
    }
}
