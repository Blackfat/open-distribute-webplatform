package com.blackfat.common.utils;

import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-09-07 19:16
 * @since 1.0-SNAPSHOT
 */
public class Converters {

    public static final Function<String, Integer> TO_INT = (s)->{ return Integer.parseInt(s); };
    public static final Function<String, Long> TO_LONG = (s)->{ return Long.parseLong(s); };
    public static final Function<String, Short> TO_SHORT = (s)->{ return Short.parseShort(s); };
    public static final Function<String, Float> TO_FLOAT = (s)->{ return Float.parseFloat(s); };
    public static final Function<String, Double> TO_DOUBLE = (s)->{ return Double.parseDouble(s); };
    public static final Function<String, Byte> TO_BYTE = (s)->{ return Byte.parseByte(s); };
    public static final Function<String, Boolean> TO_BOOLEAN = (s)->{ return Boolean.parseBoolean(s); };

    public static final Function<String, String[]> TO_ARRAY = toArray(",");

    public static final Function<String, List<String>> TO_LIST = toList(",");
    public static final Function<String, List<Integer>> TO_INTEGER_LIST = toIntegerList(",");
    public static final Function<String, List<Long>> TO_LONG_LIST = toLongList(",");

    public static final Function<String, LocalDateTime> toLocalDateTime(DateTimeFormatter dtf){
        return (s)->{ return LocalDateTime.parse(s, dtf); };
    }

    public static final Function<String, String[]> toArray(String delimiter){
        return (s)->{ return s.split(delimiter); };
    }

    public static final <T> Function<String, T> toObject(Class<T> clazz){
        return (s)->{ return JSON.parseObject(s, clazz); };
    }

    public static final Function<String, List<Integer>> toIntegerList(String delimiter){
        return (s)->{
            String[] is =  s.split(delimiter);
            List<Integer> list = new ArrayList(is.length);
            for(String i:is){ list.add(Integer.valueOf(i)); }
            return list;
        };
    }

    public static final Function<String, List<Long>> toLongList(String delimiter){
        return (s)->{
            String[] is =  s.split(delimiter);
            List<Long> list = new ArrayList(is.length);
            for(String i:is){ list.add(Long.valueOf(i)); }
            return list;
        };
    }

    public static final Function<String, List<String>> toList(String delimiter){
        return (s)->{ return Arrays.asList(s.split(delimiter)); };
    }
}
