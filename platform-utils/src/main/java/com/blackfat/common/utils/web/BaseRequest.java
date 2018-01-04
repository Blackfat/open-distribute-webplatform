package com.blackfat.common.utils.web;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/1/4-11:21
 */
public class BaseRequest {

    @ApiModelProperty(required=false, value="唯一请求号")
    private String reqNo;

    @ApiModelProperty(required=false, value="当前请求的时间戳")
    private int timeStamp;



    public BaseRequest() {
        this.setTimeStamp((int)(System.currentTimeMillis() / 1000));
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }


    @Override
    public String toString() {
        return "BaseRequest{" +
                "reqNo='" + reqNo + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
