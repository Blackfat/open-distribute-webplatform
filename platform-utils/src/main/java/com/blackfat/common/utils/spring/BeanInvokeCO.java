package com.blackfat.common.utils.spring;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 15:56
 * @since 1.0-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class BeanInvokeCO {

    String bean;
    String method;
    protected Object arg;

    public BeanInvokeCO() {
    }

    public BeanInvokeCO(String bean, String method) {
        this.bean = bean;
        this.method = method;
    }
}
