package com.blackfat.common.distributed.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author blackfat
 * @create 2019-03-24-上午11:03
 */
public class ScriptUtil {

     private static final Logger logger = LoggerFactory.getLogger(ScriptUtil.class);

    public static String getScript(String path){
        StringBuilder sb = new StringBuilder();

        InputStream input = ScriptUtil.class.getClassLoader().getResourceAsStream(path);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String str;
            while((str = br.readLine()) != null){
                sb.append(str).append(System.lineSeparator());
            }
        }catch (Exception e){
            logger.error("ScriptUtil error ",e);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getScript("limit.lua"));
    }
}
