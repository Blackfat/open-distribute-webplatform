package com.blackfat.common.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-03 15:25
 * @since 1.0-SNAPSHOT
 */
public class URLTest {


    @Test
    public void openSteamTest() throws IOException {
        URL url = new URL("https://www.baidu.com");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String data ;
        while( (data = reader.readLine()) != null){
            System.out.println(data);
        }
        reader.close();
    }



}
