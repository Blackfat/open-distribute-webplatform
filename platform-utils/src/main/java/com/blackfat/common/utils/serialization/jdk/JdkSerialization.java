package com.blackfat.common.utils.serialization.jdk;

import com.blackfat.common.utils.serialization.Serialization;

import java.io.*;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-03 23:24
 * @since 1.0-SNAPSHOT
 */
public class JdkSerialization implements Serialization {

    @Override
    public <T> void serialize(OutputStream output, T object) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(InputStream input) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(input);
        try {
            return (T) objectInputStream.readObject();// NOSONAR
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}
