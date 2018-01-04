package com.blackfat.open.platform.controller;

import com.blackfat.common.utils.web.BaseResponse;
import com.blackfat.common.utils.web.NULLBody;
import com.blackfat.common.utils.web.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;



/**
 * @author wangfeiyang
 * @desc
 * @create 2018/1/4-11:52
 */
@ControllerAdvice
public class ErrorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object processUnauthenticatedException(NativeWebRequest request, Exception e) {
        logger.error("请求出现异常:", e);
        BaseResponse<NULLBody> response = new BaseResponse<NULLBody>();
        response.setCode(StatusEnum.FAIL.getCode());
        if (e instanceof RuntimeException){
            response.setMessage(e.getMessage());
        } else {
            response.setMessage(StatusEnum.FAIL.getMessage());
        }
        return response ;
    }
}
