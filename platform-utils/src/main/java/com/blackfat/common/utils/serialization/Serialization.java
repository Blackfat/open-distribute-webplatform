package com.blackfat.common.utils.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-03 21:29
 * @since 1.0-SNAPSHOT
 */
public interface Serialization {

    /**
     * @param output OutputStream
     * @param object 对象
     * @throws IOException io异常
     */
    <T> void serialize(OutputStream output, T object) throws IOException;

    /**
     * @param input 输出流
     * @return object
     * @throws IOException io异常
     */
    <T> T deserialize(InputStream input) throws IOException;
}
