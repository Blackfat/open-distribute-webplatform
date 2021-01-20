package com.blackfat.common.utils.web;

import com.blackfat.common.utils.date.DateTimeFormatterUtil;
import com.blackfat.common.utils.date.LocalDateTimeUtil;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String s, Locale locale) throws ParseException {
        return LocalDateTimeUtil.parse(s, DateTimeFormatterUtil.TO_SECOND);
    }

    @Override
    public String print(LocalDateTime dt, Locale locale) {
        return DateTimeFormatterUtil.TO_SECOND.format(dt);
    }

}
