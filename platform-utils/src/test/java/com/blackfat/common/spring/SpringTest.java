package com.blackfat.common.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author blackfat
 * @create 2019-03-30-上午7:48
 */
public class SpringTest {

    @Test
    public void constructorTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    }
}
