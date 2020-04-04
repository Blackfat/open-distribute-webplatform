package com.blackfat.common.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-03 13:34
 * @since 1.0-SNAPSHOT
 */
public class TypeReference<T> {


    /**
     *所有类型的高级接口
     * 原始类型：   一般意义上的java类，由class类实现
     * 参数化类型：  ParameterizedType接口的实现类
     * 数组类型：   GenericArrayType接口的实现类
     * 类型变量：   TypeVariable接口的实现类
     * 基本类型：   int，float等java基本类型，其实也是class
     */
    private final Type type;


    /**
     * getSuperclass():返回直接继承的父类
     *
     * getGenericSuperclass():返回直接继承的父类(包含范型)
     */
    public TypeReference(){
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.type = type;
    }

    public Type getType() {
        return type;
    }


    public static void main(String[] args) {
        TypeReference<List<String>> typeReference = new TypeReference<List<String>>(){};
        System.out.println(typeReference.getType());
    }


}
