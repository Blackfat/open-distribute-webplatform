package com.blackfat.open.platform.controller;

import com.blackfat.common.utils.web.BaseRequest;
import com.blackfat.open.platform.interceptor.CheckReqNo;
import com.wordnik.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/1/4-11:47
 */

@Controller
@RequestMapping(value="/checkReq")
@Api(value = "/checkReq", description = "防止重复请求")
public class CheckReqNoController {

    @CheckReqNo
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody BaseRequest baseRequest){
        return "create" ;
    }
}
