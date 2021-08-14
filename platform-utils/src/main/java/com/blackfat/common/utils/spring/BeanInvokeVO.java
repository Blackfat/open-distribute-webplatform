package com.blackfat.common.utils.spring;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Date;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 15:57
 * @since 1.0-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class BeanInvokeVO extends BeanInvokeCO {

    String invoker;
    long cost;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date start;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date end;
    String retStr;
    String error;

    public BeanInvokeVO() {
    }

    public BeanInvokeVO(String bean, String method) {
        super(bean, method);
    }


    public void invoke(Object arg, String invoker){
        this.start = new Date();
        this.arg = arg;
        this.invoker = invoker;
    }

    public void end(){
        this.end = new Date();
        if(start==null){ start = end; }
        this.cost = end.getTime()-start.getTime();
    }

    public BeanInvokeVO done(String retStr){
        end();
        this.retStr = retStr;
        this.error = null;
        return this;
    }

    public BeanInvokeVO fail(Throwable error){
        end();
        this.retStr = null;
        this.error = error==null ? null: ExceptionUtils.getStackTrace(error);
        return this;
    }
}
