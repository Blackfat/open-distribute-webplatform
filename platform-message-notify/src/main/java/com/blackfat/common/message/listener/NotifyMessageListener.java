package com.blackfat.common.message.listener;


import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/16-14:20
 */
@Component
public class NotifyMessageListener implements SessionAwareMessageListener<Message> {


    private Logger logger = LoggerFactory.getLogger(NotifyMessageListener.class);


    @Autowired
    private JmsTemplate jmsTemplate;


    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        try{
            ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
            final String msgText = msg.getText();
            logger.info("------------------------------>>"+msgText);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
