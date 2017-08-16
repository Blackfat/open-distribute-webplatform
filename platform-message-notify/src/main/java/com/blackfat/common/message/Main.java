package com.blackfat.common.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/16-14:52
 */
public class Main {

    private Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        try{
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });

            applicationContext.start();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}


