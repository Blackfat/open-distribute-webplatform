package com.blackfat.common.utils.groovy;

import com.blackfat.common.utils.security.SecurityUtils;
import com.google.common.collect.Maps;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;
import lombok.extern.slf4j.Slf4j;


import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-08-14 14:27
 * @since 1.0-SNAPSHOT
 */
@Slf4j
public class GroovyScriptFactory {

    private static Map<String, Class<Script>> scriptCache = Maps.newConcurrentMap();

    private GroovyClassLoader classLoader = new GroovyClassLoader();

    private static GroovyScriptFactory factory = new GroovyScriptFactory();

    private GroovyScriptFactory() {
    }

    public static GroovyScriptFactory getInstance() {
        return factory;
    }

    private Class getScript(String key) {
        // 压缩脚本节省空间
        String encodeStr = null;
        try {
            encodeStr = SecurityUtils.md5(key, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            log.error("",e);
        }
        Class<Script> script = scriptCache.get(encodeStr);
        if(script == null){
            // 脚本不存在则创建新的脚本
            Class<Script> scriptClass = classLoader.parseClass(key);
            scriptCache.putIfAbsent(encodeStr, scriptClass);
            script = scriptCache.get(encodeStr);
        }
        return script;
    }

    private void doRun(Class<Script> groovyClass) throws IllegalAccessException, InstantiationException {
        Object[] param = {};
        GroovyObject groovyObject =  groovyClass.newInstance();
        groovyObject.invokeMethod("invoke",param);
        // 每次脚本执行完之后，一定要清理掉内存
        classLoader.clearCache();
    }

    public void run(String key) {

        try {
            doRun(getScript(key));
        } catch (Exception e) {
            log.error("",e);
        }
    }
}
