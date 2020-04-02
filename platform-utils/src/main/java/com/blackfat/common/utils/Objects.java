package com.blackfat.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-02 14:42
 * @since 1.0-SNAPSHOT
 */
public class Objects {

    public static String toQuery(Object o) throws UnsupportedEncodingException {
        Map<String, Object> map = null;
        if (o instanceof Map) {
            map = (Map) o;
        } else {
            map = JSON.parseObject(JSON.toJSONString(o));
        }
        String[] list = new String[map.size()];
        int i = 0;
        for (Map.Entry<String, Object> e : map.entrySet()) {
            list[i++] = e.getKey() + "=" + URLEncoder.encode(e.getValue().toString(), "UTF-8");
        }
        return StringUtils.join(list, '&');
    }
}
