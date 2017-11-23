package com.blackfat.common.guava;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/11/22-10:54
 */
public class People {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public People() {
    }

    public People(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
