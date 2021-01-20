package com.blackfat.common.utils.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
public class LongToStringSerializer extends StdSerializer<Long> {
    public static final LongToStringSerializer instance = new LongToStringSerializer();

    static final ToStringSerializer stringSerializer = ToStringSerializer.instance;
    static final NumberSerializers.LongSerializer longSerializer = new NumberSerializers.LongSerializer(Long.class);

    public LongToStringSerializer() {
        super(Long.class);
    }

    public LongToStringSerializer(Class<?> handledType) {
        super(handledType, false);
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, Long value) {
        return longSerializer.isEmpty(prov, value);
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String path = HttpJsonConfig.enablePath();
        if (path == null) {
            longSerializer.serialize(value, gen, provider);
        } else {
            log.warn("serialize at " + path);
            stringSerializer.serialize(value, gen, provider);
        }
    }

    @Override
    public void serializeWithType(Long value, JsonGenerator g, SerializerProvider provider, TypeSerializer typeSer)
        throws IOException {
        String path = HttpJsonConfig.enablePath();
        if (path == null) {
            longSerializer.serializeWithType(value, g, provider, typeSer);
        } else {
            log.warn("serializeWithType at " + path);
            stringSerializer.serializeWithType(value, g, provider, typeSer);
        }
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        String path = HttpJsonConfig.enablePath();
        if (path == null) {
            return longSerializer.getSchema(provider, typeHint);
        } else {
            log.warn("getSchema at " + path);
            return stringSerializer.getSchema(provider, typeHint);
        }
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
        throws JsonMappingException {
        String path = HttpJsonConfig.enablePath();
        if (path == null) {
            longSerializer.acceptJsonFormatVisitor(visitor, typeHint);
        } else {
            log.warn("acceptJsonFormatVisitor at " + path);
            stringSerializer.acceptJsonFormatVisitor(visitor, typeHint);
        }
    }
}
