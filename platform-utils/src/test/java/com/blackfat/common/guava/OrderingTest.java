package com.blackfat.common.guava;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/11/22-10:52
 */
public class OrderingTest {

    @Test
    public void testNatural(){
        List<People> peopleList = new ArrayList<People>() {{
            add(new People("A", 33));
            add(new People("B", 11));
            add(new People("C", 18));
        }};

        Ordering<People> ordering = Ordering.natural().onResultOf(new Function<People, Comparable>(){
            @Override
            public Comparable apply(People people) {
                return people.getAge();
            }
        });


        for(People p : ordering.sortedCopy(peopleList)){
            System.out.println(MoreObjects.toStringHelper(p)
                    .add("name", p.getName())
                    .add("age", p.getAge())
            );
        }
    }

    @Test
    public void usingToString(){
         List<People> peopleList = new ArrayList<People>() {{
            add(new People("A", 33));
            add(new People("B", 11));
            add(new People("C", 18));
        }};

        Ordering<People> ordering = Ordering.usingToString().onResultOf(new Function<People, Comparable>() {
            @Override
            public Comparable apply(People people) {
                return people.getName();
            }
        });

        for(People p:ordering.sortedCopy(peopleList)){
            System.out.println(MoreObjects.toStringHelper(p)
                    .add("name",p.getName())
                    .add("age",p.getAge()));
        }



    }

    @Test
    public void testFrom(){
         List<People> peopleList = new ArrayList<People>() {{
            add(new People("A", 33));
            add(new People("B", 11));
            add(new People("C", 18));
        }};

         Ordering<People> ordering = Ordering.from(new Comparator<People>() {
             @Override
             public int compare(People o1, People o2) {
                 return o1.getAge() - o2.getAge();
             }
         });

         for(People p : ordering.sortedCopy(peopleList)){
             System.out.println(MoreObjects.toStringHelper(p)
                     .add("name",p.getName())
                     .add("age",p.getAge()));

         }




    }


}
