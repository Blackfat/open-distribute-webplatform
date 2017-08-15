package com.blackfat.common.api.message.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/15-10:58
 */
public class MessageInfo implements Serializable {

    private static final long serialVersionUID = 9155437835867302347L;

    private Integer id;

    private String messageId;

    private String messageBody;

    private String dataType;

    private String consumerQueue;

    private Integer sendTimes;

    private Integer isDead;

    private String remark;

    private Date createTime;

    private Integer status;

    public MessageInfo() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getConsumerQueue() {
        return consumerQueue;
    }

    public void setConsumerQueue(String consumerQueue) {
        this.consumerQueue = consumerQueue;
    }

    public Integer getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Integer getIsDead() {
        return isDead;
    }

    public void setIsDead(Integer isDead) {
        this.isDead = isDead;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
