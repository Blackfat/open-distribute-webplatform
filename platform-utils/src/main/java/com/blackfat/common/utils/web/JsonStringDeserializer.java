package com.blackfat.common.utils.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonStringDeserializer extends StringDeserializer {

    public static final JsonStringDeserializer instance = new JsonStringDeserializer();

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String s = super.deserialize(p, ctxt);
        String path = HttpJsonConfig.enablePath();
        if (s == null || path == null || !s.contains("\\u0000")) {
            return s;
        }
        String _s = s.replaceAll("\\\\u0000", "");
        log.warn("deserialize " + s + " to " + _s + " at " + path);
        return _s;
    }

}
