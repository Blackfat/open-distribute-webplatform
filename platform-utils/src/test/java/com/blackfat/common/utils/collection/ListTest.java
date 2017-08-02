package com.blackfat.common.utils.collection;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/1-8:47
 */
public class ListTest {


    @Test
    public void subListTest() {
        List<Integer> list = Lists.newArrayList(1,2,3,4,5);

        List<Integer> subList = list.subList(1,3);

        for(Integer sub : subList){
            System.out.println("subList:"+sub);
        }

        subList.set(0,6);

        for(Integer parent:list){
            System.out.println("parentList:"+parent);
        }

    }

    @Test
    public void subListTest2(){
        List<Integer> list = Lists.newArrayList(1,2,3,4,5);

        list.subList(1,3).clear();

        for(Integer parent:list){
            System.out.println("parentList:"+parent);
        }


    }

    @Test
    public void subListTest3() {
        List<Integer> list = Lists.newArrayList(1,2,3,4,5);

        List<Integer> subList = list.subList(1,3);

        list.add(6);

        for(Integer sub : subList){
            System.out.println("subList:"+sub);
        }

    }


}
