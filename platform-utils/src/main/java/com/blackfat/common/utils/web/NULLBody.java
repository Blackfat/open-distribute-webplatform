package com.blackfat.common.utils.web;

/**
 * @author wangfeiyang
 * @desc  用在泛型中,表示没有额外的请求参数或者返回参数
 * @create 2018/1/4-11:55
 */
public class NULLBody {

    public NULLBody() {}

    public static NULLBody create(){
        return new NULLBody();
    }
}
