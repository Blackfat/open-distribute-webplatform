package com.blackfat.common.utils.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-30 17:39
 * @since 1.0-SNAPSHOT
 */
public class DateTimeFormatterUtil {



    public static final DateTimeFormatter
            TO_DAY = DateTimeFormatter.ofPattern(DateFormats.TO_DAY),
            TO_DAY_ZH_CN = DateTimeFormatter.ofPattern(DateFormats.TO_DAY_ZH_CN),
            TO_MINUTE = DateTimeFormatter.ofPattern(DateFormats.TO_MINUTE),
            TO_SECOND = DateTimeFormatter.ofPattern(DateFormats.TO_SECOND),
            TO_MILLI = DateTimeFormatter.ofPattern(DateFormats.TO_MILLI),
            yyyyMMdd = DateTimeFormatter.ofPattern(DateFormats.yyyyMMdd),
            yyyyMMddHHmmss = DateTimeFormatter.ofPattern(DateFormats.yyyyMMddHHmmss)
                    ;

    public static final DateTimeFormatter
            yyyy_MM_dd = TO_DAY,
            yyyy_MM_dd_HH_mm_ss = TO_SECOND
                    ;

    public static LocalDateTime parse(String s) {
        int l = s.length();
        if(l == DateFormats.TO_DAY.length()){ return LocalDateTimeUtil.parse(s, TO_DAY); }
        else if(l == DateFormats.TO_MINUTE.length()){ return LocalDateTimeUtil.parse(s, TO_MINUTE); }
        else if(l == DateFormats.TO_SECOND.length()){ return LocalDateTimeUtil.parse(s, TO_SECOND); }
        else if(l == DateFormats.TO_MILLI.length()){ return LocalDateTimeUtil.parse(s, TO_MILLI); }
        else if(l == DateFormats.yyyyMMddHHmmss.length()){return LocalDateTimeUtil.parse(s, yyyyMMddHHmmss);}
        throw new IllegalArgumentException();
    }
}
