package com.blackfat.common.lambda;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-03-18 08:55
 * @since 1.0-SNAPSHOT
 */
public class StreamTest {


    /**
     * 通过 stream 方法把 List 或数组转换为流；
     */
    @Test
    public void stream() {
        Arrays.asList("a", "b", "c").stream().forEach(System.out::println);
        Arrays.stream(new int[]{1, 2, 3}).forEach(System.out::println);
    }

    /**
     * 通过Stream.of方法直接传入多个元素构成一个流
     */
    @Test
    public void of() {
        String[] arr = {"a", "b", "c"};
        Stream.of(arr).forEach(System.out::println);
        Stream.of("a", "b", "c").forEach(System.out::println);
        Stream.of(1, 2, "a").map(item -> item.getClass().getName()).forEach(System.out::println);
    }


    //通过Stream.iterate方法使用迭代的方式构造一个无限流，然后使用limit限制流元素个数
    @Test
    public void iterate() {
        Stream.iterate(2, item -> item * 2).limit(10).forEach(System.out::println);
        Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.TEN)).limit(10).forEach(System.out::println);
    }

    // 通过Stream.generate方法从外部传入一个提供元素的Supplier来构造无限流，然后使用limit限制
    @Test
    public void generate(){
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    // 通过IntStream或DoubleStream构造基本类型的流
    @Test
    public void primitive(){
        IntStream.range(1,3).forEach(System.out::println);
        IntStream.range(0, 3).mapToObj(i -> "x").forEach(System.out::println);


        IntStream.rangeClosed(1, 3).forEach(System.out::println);
        DoubleStream.of(1.1, 2.2, 3.3).forEach(System.out::println);


        //各种转换，后面注释代表了输出结果
        System.out.println(IntStream.of(1, 2).toArray().getClass()); //class [I
        System.out.println(Stream.of(1, 2).mapToInt(Integer::intValue).toArray().getClass()); //class [I
        System.out.println(IntStream.of(1, 2).boxed().toArray().getClass()); //class [Ljava.lang.Object;
        System.out.println(IntStream.of(1, 2).asDoubleStream().toArray().getClass()); //class [D
        System.out.println(IntStream.of(1, 2).asLongStream().toArray().getClass()); //class [J

        Arrays.asList("a", "b", "c").stream()
                .mapToInt(String::length)
                .asLongStream()
                .mapToDouble(x -> x /10.0)
                .boxed()
                .mapToLong(x  -> 1l)
                .mapToObj(x->"")
                .collect(Collectors.toList());

    }


}
