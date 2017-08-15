package com.blackfat.common.api.message.enums;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/15-11:07
 */
public enum MessageStatusEnum {

    WAITING_CONFIRM("待确认", 0),

    SENDING("发送中", 1);

    private String desc;

    private Integer value;

    private MessageStatusEnum(String desc, Integer value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
