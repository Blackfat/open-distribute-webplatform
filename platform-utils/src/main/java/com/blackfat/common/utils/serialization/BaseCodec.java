package com.blackfat.common.utils.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-03 23:21
 * @since 1.0-SNAPSHOT
 */
public class BaseCodec {

    protected byte[] encode(SerializationPool pool, Object message) throws IOException {
        Serialization serialization = pool.borrow();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            serialization.serialize(baos, message);
            return baos.toByteArray();
        } finally {
            pool.restore(serialization);
        }
    }

    protected <T> T decode(SerializationPool pool, byte[] body) throws IOException {
        Serialization serialization = pool.borrow();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(body)) {
            return serialization.deserialize(bais);
        } finally {
            pool.restore(serialization);
        }
    }
}
