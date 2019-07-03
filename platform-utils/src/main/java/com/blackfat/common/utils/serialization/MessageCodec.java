package com.blackfat.common.utils.serialization;

import java.io.IOException;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-03 23:19
 * @since 1.0-SNAPSHOT
 */
public interface MessageCodec {
    /**
     * @param message obj to be encoded
     * @throws IOException exception
     */
    byte[] encode(Object message) throws IOException;

    /**
     * @param body bytes to be decoded
     * @return Object obj
     * @throws IOException exception
     */
    <T> T decode(byte[] body) throws IOException;
}
