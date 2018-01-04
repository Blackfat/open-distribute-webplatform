package com.blackfat.common.utils.web;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/1/4-11:54
 */
public class BaseResponse<T> implements Serializable {

    private String code;

    private String message;

    /**
     * 请求号
     */
    private String reqNo;

    private T dataBody;

    public BaseResponse() {
    }

    public BaseResponse(T dataBody) {
        this.dataBody = dataBody;
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(String code, String message, T dataBody) {
        this.code = code;
        this.message = message;
        this.dataBody = dataBody;
    }

    public BaseResponse(String code, String message, String reqNo, T dataBody) {
        this.code = code;
        this.message = message;
        this.reqNo = reqNo;
        this.dataBody = dataBody;
    }

    public static <T> BaseResponse<T> create(T t) {
        return new BaseResponse<T>(t);
    }

    public static <T> BaseResponse<T> create(T t, StatusEnum statusEnum) {
        return new BaseResponse<T>(statusEnum.getCode(), statusEnum.getMessage(), t);
    }

    public static <T> BaseResponse<T> createSuccess(T t, String message) {
        return new BaseResponse<T>(StatusEnum.SUCCESS.getCode(), StringUtils.isEmpty(message) ? StatusEnum.SUCCESS.getMessage() : message, t);
    }

    public static <T> BaseResponse<T> createFail(T t, String message) {
        return new BaseResponse<T>(StatusEnum.FAIL.getCode(), StringUtils.isEmpty(message) ? StatusEnum.FAIL.getMessage() : message, t);
    }

    public static <T> BaseResponse<T> create(T t, StatusEnum statusEnum, String message) {

        return new BaseResponse<T>(statusEnum.getCode(), message, t);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDataBody() {
        return dataBody;
    }

    public void setDataBody(T dataBody) {
        this.dataBody = dataBody;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }


}
