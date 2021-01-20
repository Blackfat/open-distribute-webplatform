package com.blackfat.common.utils.date;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-30 17:38
 * @since 1.0-SNAPSHOT
 */
public class LocalDateTimeUtil {


    public static LocalDateTime ofMilli(Long milli) {
        return ofMilli(milli, null);
    }

    public static LocalDateTime ofMilli(Long milli, LocalDateTime def) {
        return of(Instant.ofEpochMilli(milli), def);
    }


    public static LocalDateTime ofSecond(Long second) {
        return ofSecond(second, null);
    }

    public static LocalDateTime ofSecond(Long second, LocalDateTime def) {
        return of(Instant.ofEpochSecond(second), def);
    }

    public static LocalDateTime of(Instant instant, LocalDateTime def) {
        if(instant == null){ return def; }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }

    public static LocalDateTime parse(String text, DateTimeFormatter formatter, LocalDateTime def) {
        if(StringUtils.isEmpty(text)){ return def; }
        return LocalDateTime.parse(text, formatter);
    }

    public static LocalDateTime parse(String text, DateTimeFormatter formatter) {
        return parse(text, formatter, null);
    }

    public static LocalDateTime parseDate(String text) {
        return parse(text, DateTimeFormatterUtil.yyyy_MM_dd);
    }

    public static long toSecond(LocalDateTime ldt) {
        return ldt.toEpochSecond(ZoneOffsetUtil.get());
    }

    public static long toMilli(LocalDateTime ldt) {
        return ldt.toInstant(ZoneOffsetUtil.get()).toEpochMilli();
    }


    public static LocalDate plusYear(LocalDate date, Long time){
        return date.plusYears(time);
    }


    public static boolean equal(LocalDateTime ldt, Date d) {
        if(ldt == null && d == null){ return true; }
        if(ldt == null || d == null){ return false; }
        return toMilli(ldt) == d.getTime();
    }
}
