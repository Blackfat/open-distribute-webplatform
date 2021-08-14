package com.blackfat.common.utils.spring;

import lombok.Data;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:10
 * @since 1.0-SNAPSHOT
 */
@Data
public class CallContext {
    String api;
    Object operator;
}
