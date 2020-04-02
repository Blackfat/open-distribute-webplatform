package com.blackfat.common.utils.placeholder;

import lombok.extern.slf4j.Slf4j;

/**
 * 安全的ObjectResolver，异常是会进行处理并返回默认值
 */
@Slf4j
public class ObjectSafeResolver extends ObjectResolver {
    String errValue;

    public ObjectSafeResolver(Object defTarget, String errValue, Route... routes) {
        super(defTarget, routes);
        this.errValue = errValue;
    }

    @Override
    public String resolvePlaceholder(String key) {
        try {
            return super.resolvePlaceholder(key);
        }catch (Throwable e){
            log.error("resolvePlaceholder failed use["+errValue+"]: "+e.getMessage());
        }
        return errValue;
    }
}
