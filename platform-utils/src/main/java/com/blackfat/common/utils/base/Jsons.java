package com.blackfat.common.utils.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:13
 * @since 1.0-SNAPSHOT
 */
public class Jsons {

    public static String toJsonString(Object o) {
        return JSON.toJSONString(o);
    }

    public static String toJsonStringDisableCircularReference(Object o){
        return JSON.toJSONString(o, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String toJsonStringWithMapNullValue(Object o){
        return  JSON.toJSONString(o, SerializerFeature.WriteMapNullValue);
    }

    public static JSONObject toJsonObject(String o) {
        return JSON.parseObject(o);
    }

    public static <T> T toClass(String o, Class<T> tClass) {
        return JSON.parseObject(o, tClass);
    }

    public static Map<String, ?> toMap(String o) {
        return JSON.parseObject(o, new TypeReference<Map<String, ?>>() {

        });
    }

    public static Map<String, String> toStringMap(String o) {
        return JSON.parseObject(o, new TypeReference<Map<String, String>>() {

        });
    }
}
