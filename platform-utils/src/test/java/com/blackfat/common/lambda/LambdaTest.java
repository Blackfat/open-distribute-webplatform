package com.blackfat.common.lambda;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-02-19 11:18
 * @since 1.0-SNAPSHOT
 */
public class LambdaTest {


    @Test
    public void  lambda1(){
        IntPredicate predicate = (int i) -> i % 2 == 0;
        Assert.assertTrue(predicate.test(1000));
        Assert.assertTrue(!predicate.test(1001));
    }

    @Test
    public void lambda2(){
        List list = Lists.newArrayList();
        Predicate<String> p = s -> list.add(s);
        Consumer<String> c = s -> list.add(s);
    }
}
