package com.blackfat.common.utils.serialization.jdk;

import com.blackfat.common.utils.serialization.Serialization;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-03 23:23
 * @since 1.0-SNAPSHOT
 */
public class JdkSerializationFactory extends BasePooledObjectFactory<Serialization> {

    @Override
    public Serialization create() {
        return createSerialization();
    }

    @Override
    public PooledObject<Serialization> wrap(final Serialization serialization) {
        return new DefaultPooledObject<>(serialization);
    }

    private Serialization createSerialization() {
        return new JdkSerialization();
    }

}
