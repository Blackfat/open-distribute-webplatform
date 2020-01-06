package com.blackfat.common.utils.bean;

import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.ConfigFileChangeListener;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.ctrip.framework.apollo.model.ConfigFileChangeEvent;

import java.util.function.Function;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-12-31 14:03
 * @since 1.0-SNAPSHOT
 */
public class ApolloFileHolder<T> extends AbstractFreshBeanHolder<String, T> implements ConfigFileChangeListener {
    String namespace;
    ConfigFileFormat fileFormat;
    ConfigFile configFile;

    public ApolloFileHolder(String namespace, ConfigFileFormat fileFormat, Function<String, T> builder, String initContent) {
        super(builder, initContent);
        this.namespace = namespace;
        this.fileFormat = fileFormat;
        this.configFile = Apollos.getFile(namespace, fileFormat);
        configFile.addChangeListener(this);
    }

    public ApolloFileHolder(String namespace, ConfigFileFormat fileFormat, Function<String, T> builder) {
        this(namespace, fileFormat, builder, null);
    }

    @Override
    protected String getInitArg() {
        return configFile.hasContent() ? configFile.getContent():initArg;
    }

    @Override
    public void onChange(ConfigFileChangeEvent e) {
        doFresh(e.getNewValue());
    }


}
