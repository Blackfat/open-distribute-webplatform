package com.blackfat.common.utils.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Configuration
public class HttpJsonConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
        registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, LongToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, LongToStringSerializer.instance);

        /**
         * 序列换成json时,将所有的long变成string 因为js中得数字类型不能包含所有的java long值
         */

        converters.forEach((c -> {
            if (c instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jc = (MappingJackson2HttpMessageConverter)c;
                jc.getObjectMapper().registerModule(simpleModule)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);
            }
        }));

    }

    public static String enablePath() {
        String pathPattern = RequestUtils.getAttribute(null, HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        if (pathPattern == null) {
            return null;
        }
        if (pathPattern.startsWith("/admin")) {
            return pathPattern;
        } else {
            return null;
        }
    }

}
