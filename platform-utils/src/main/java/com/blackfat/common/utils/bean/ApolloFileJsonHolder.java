package com.blackfat.common.utils.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;


/**
 * @author wangfeiyang
 * @Description
 * @create 2019-12-31 14:04
 * @since 1.0-SNAPSHOT
 */
public class ApolloFileJsonHolder<T> extends ApolloFileHolder<T> {

    public ApolloFileJsonHolder(String namespace, Class<T> clazz, String initContent) {
        super(namespace, ConfigFileFormat.JSON, (s)-> JSON.parseObject(s, clazz), initContent);
    }

    public ApolloFileJsonHolder(String namespace, Class<T> clazz) {
        this(namespace, clazz, null);
    }

    public ApolloFileJsonHolder(String namespace, TypeReference<T> typeRef, String initContent) {
        super(namespace, ConfigFileFormat.JSON, (s)-> JSON.parseObject(s, typeRef), initContent);
    }

    public ApolloFileJsonHolder(String namespace, TypeReference<T> typeRef) {
        this(namespace, typeRef, null);
    }
}
