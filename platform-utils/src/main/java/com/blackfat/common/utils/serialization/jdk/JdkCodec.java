package com.blackfat.common.utils.serialization.jdk;

import com.blackfat.common.utils.serialization.BaseCodec;
import com.blackfat.common.utils.serialization.MessageCodec;
import com.blackfat.common.utils.serialization.SerializationPool;

import java.io.IOException;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-03 23:25
 * @since 1.0-SNAPSHOT
 */
public class JdkCodec extends BaseCodec implements MessageCodec {

    private final SerializationPool pool = SerializationPool
            .builder()
            .pooledObjectFactory(new JdkSerializationFactory())
            .build();

    @Override
    public byte[] encode(Object message) throws IOException {
        return encode(pool, message);
    }

    @Override
    public <T> T decode(byte[] body) throws IOException {
        return decode(pool, body);
    }

}
