package com.blackfat.common.reflect.annoation;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-23 16:47
 * @since 1.0-SNAPSHOT
 */
@MyAnnotation(value = "class" )
public class Parent {

    @MyAnnotation(value="method")
    public void invoke(){

    }
}
