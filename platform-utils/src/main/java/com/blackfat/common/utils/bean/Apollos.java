package com.blackfat.common.utils.bean;

import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-12-31 14:07
 * @since 1.0-SNAPSHOT
 */
public class Apollos {

    public static ConfigFile getFile(String namespace, ConfigFileFormat configFileFormat){
        return ConfigService.getConfigFile(namespace, configFileFormat);
    }
}
