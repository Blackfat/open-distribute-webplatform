package com.blackfat.common.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-23 14:27
 * @since 1.0-SNAPSHOT
 */
@Slf4j
public class ReflectionIssue {

    private void age(int age) {
        log.info("int age = {}", age);
    }

    private void age(Integer age) {
        log.info("Integer age = {}", age);
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ReflectionIssue issue = new ReflectionIssue();
        issue.getClass().getDeclaredMethod("age", Integer.class).invoke(issue, Integer.valueOf("36"));
    }
}
