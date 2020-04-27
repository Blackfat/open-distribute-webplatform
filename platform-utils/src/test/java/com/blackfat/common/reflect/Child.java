package com.blackfat.common.reflect;

import java.util.Arrays;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-23 15:59
 * @since 1.0-SNAPSHOT
 */
public class Child extends Parent<String> {

    @Override
    public void setValue(String value) {
        System.out.println("Child.setValue called");
        super.setValue(value);
    }


    public static void main(String[] args) {
        Child child = new Child();
        //synthetic 代表由编译器生成的不可见代码，bridge 代表这是泛型类型擦除后生成的桥接代码
        Arrays.stream(child.getClass().getMethods()).filter(method -> method.getName().equals("setValue")).forEach(method -> {
            try {
                method.invoke(child, "test");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println(child.toString());
    }
}
