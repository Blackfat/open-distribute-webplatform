package com.blackfat.common.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 本地时间+时区差=UTC时间(原子钟为基础的统一时间，不以太阳参照计时，并无时区划分)
 * Date：时间戳 UTC时间 代表的是从 1970 年 1 月 1 日 0 点（Epoch 时间）到现在的毫秒数
 * LocalDate: 表示日期，无时区属性
 * LocalTime: 表示时间，无时区属性
 * LocalDateTime:LocalDate+LocalTime，无时区属性
 * Instant: 时间戳，类似Date
 * ZonedDateTime:LocalDate+LocalTime，有时区属性
 * Duration:一段时间
 * Period:一段日期
 * DateTimeFormatter:日期的格式化，线程安全
 * ZoneId/ZoneOffset:时区
 * @author wangfeiyang
 * @Description
 * @create 2020-04-17 09:44
 * @since 1.0-SNAPSHOT
 */
/** mysql
 * TIMESTAMP保存的时候根据当前时区转换为UTC，查询的时候再根据当前时区从UTC转回来，而DATETIME就是一个死的字符串时间
 * datetime占用8字节，不受时区影响，表示范围'1000-01-01 00:00:00' to '9999-12-31 23:59:59'
 * timestamp占用4字节，受时区影响，表示范围'1970-01-01 00:00:01' to '2038-01-19 03:14:07'，若插入null会自动转化为当前时间
 */
public class DateTest {


    @Test
    public void zoneTimeTest(){
         //一个时间表示
        String stringDate = "2020-01-02 22:00:00";
        //初始化三个时区
        ZoneId timeZoneSH = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNY = ZoneId.of("America/New_York");
        ZoneId timeZoneJST = ZoneOffset.ofHours(9);
         //格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.parse(stringDate, dateTimeFormatter), timeZoneJST);
         //使用DateTimeFormatter格式化时间，可以通过withZone方法直接设置格式化使用的时区
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println(timeZoneSH.getId() + outputFormat.withZone(timeZoneSH).format(date));
        System.out.println(timeZoneNY.getId() + outputFormat.withZone(timeZoneNY).format(date));
        System.out.println(timeZoneJST.getId() + outputFormat.withZone(timeZoneJST).format(date));
    }

    @Test
    public void periodTest(){
        System.out.println("//计算日期差");
        LocalDate today = LocalDate.of(2019, 12, 12);
        LocalDate specifyDate = LocalDate.of(2019, 10, 1);
        System.out.println(Period.between(specifyDate, today).getDays());
        System.out.println(Period.between(specifyDate, today));
        System.out.println(ChronoUnit.DAYS.between(specifyDate, today));
    }


    @Test
    public void DateToLocalDateTime(){
        Date in = new Date();
        // toString的时候处理了时区
        System.out.println(in);
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        System.out.println(ldt);
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(out);
    }

    @Test
    public void InstantTest(){
        // utc 时间
        Instant now = Instant.now();
        System.out.println("now:"+now);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime:"+localDateTime);
    }
}
