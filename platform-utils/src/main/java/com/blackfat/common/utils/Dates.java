package com.blackfat.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 使用joda-time封装日期的基本操作
 */
public class Dates {

    /**
     * 日期时间格式内容
     */
    private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式内容
     */
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式内容
     */
    private static final String TIME_FORMAT_PATTERN = "HH:mm:ss";

    /**
     * 将日期格式化成字符串
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static String formatDate(Date date, String formatPattern) {
        if (StringUtils.isEmpty(formatPattern)) {
            formatPattern = DATETIME_FORMAT_PATTERN;
        }

        if (date == null) {
            return "";
        }

        DateTimeFormatter dtf = DateTimeFormat.forPattern(formatPattern);
        DateTime datetime = new DateTime(date);

        return dtf.print(datetime);
    }

    /**
     * 将日期字符串解析成日期
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static Date parseDate(String date, String formatPattern) {
        if (StringUtils.isEmpty(formatPattern)) {
            formatPattern = DATETIME_FORMAT_PATTERN;
        }

        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormat.forPattern(formatPattern);

        return dtf.parseDateTime(date).toDate();
    }

}
