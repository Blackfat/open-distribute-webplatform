package com.blackfat.common.utils.date;

import java.time.ZoneOffset;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-30 17:44
 * @since 1.0-SNAPSHOT
 */
public class ZoneOffsetUtil {

    public static final ZoneOffset E8 = ZoneOffset.of("+8");

    public static ZoneOffset get(){ return E8; }
}
