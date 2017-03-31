package com.blackfat.common.utils.collection;

import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.blackfat.common.utils.base.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 数组工具类
 *
 * @author blackfat
 * @create 2017-03-10 下午1:06
 */
public class ArrayUtil {

    /*
    *  将传入的数组乱序
    * */
    public static <T>  T[] shuffle(T[] array){
        List<T> list = new ArrayList<T>(array.length);
        Collections.addAll(list, array);
        Collections.shuffle(list);
        return list.toArray(array);
    }

    /*
   *  将传入的数组乱序
   * */
    public static <T> T[] shuffle(T[] array, Random random){
        List<T> list = new ArrayList<T>(Arrays.asList(array));
        Collections.shuffle(list, random);
        return list.toArray(array);
    }

    /**
     * 添加元素到数组头,复制扩容.
     */
    public static <T> T[] concat(@Nullable T element, T[] array) {
        return ObjectArrays.concat(element, array);
    }

    /**
     * 添加元素到数组末尾，复制扩容.
     */
    public static <T> T[] concat(T[] array, @Nullable T element) {
        return ObjectArrays.concat(array, element);
    }

    /**
     * 传入类型与大小创建数组.
     */
    public static <T> T[] newArray(Class<T> type, int length){
          return (T[])Array.newInstance(type, length);
    }

    /**
     * Arrays.asList()的加强版, 返回一个底层为原始类型int的List
     *
     * 与保存Integer相比节约空间，同时只在读取数据时AutoBoxing.
     *
     * @see java.util.Arrays#asList(Object...)
     * @see com.google.common.primitives.Ints#asList(int...)
     *
     */
    public static List<Integer> intAsList(int... backingArray) {
        return Ints.asList(backingArray);
    }

    /**
     * Arrays.asList()的加强版, 返回一个底层为原始类型long的List
     *
     * 与保存Long相比节约空间，同时只在读取数据时AutoBoxing.
     *
     * @see java.util.Arrays#asList(Object...)
     * @see com.google.common.primitives.Longs#asList(long...)
     */
    public static List<Long> longAsList(long... backingArray) {
        return Longs.asList(backingArray);
    }

    /**
     * Arrays.asList()的加强版, 返回一个底层为原始类型double的List
     *
     * 与保存Double相比节约空间，同时也避免了AutoBoxing.
     *
     * @see java.util.Arrays#asList(Object...)
     * @see com.google.common.primitives.Doubles#asList(double...)
     */
    public static List<Double> doubleAsList(double... backingArray) {
        return Doubles.asList(backingArray);
    }
}
