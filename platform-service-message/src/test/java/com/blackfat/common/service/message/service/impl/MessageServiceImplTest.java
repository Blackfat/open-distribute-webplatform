package com.blackfat.common.service.message.service.impl;

import com.blackfat.common.api.message.entity.MessageInfo;
import com.blackfat.common.api.message.service.IMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/spring-context.xml"})
public class MessageServiceImplTest {


    @Autowired
    private IMessageService  messageService;

    @Test
    public void sendMessageTest() throws Exception {
        MessageInfo messageInfo = new MessageInfo();

        messageInfo.setConsumerQueue("black-queue");
        messageInfo.setMessageBody("hello");

        messageService.sendMessage(messageInfo);


    }

}