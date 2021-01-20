package com.blackfat.common.utils.web;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String s, Locale locale) {
        return LocalDate.parse(s);
    }

    @Override
    public String print(LocalDate d, Locale locale) {
        return d.toString();
    }
}
