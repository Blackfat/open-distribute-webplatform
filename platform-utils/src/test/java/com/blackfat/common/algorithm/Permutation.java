package com.blackfat.common.algorithm;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-11-05 18:55
 * @since 1.0-SNAPSHOT
 */
public class Permutation {

    public static List<List<Integer>> permute(List<Integer> nums){
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> track = new ArrayList<>();
        backTrack(nums,track,result);
        return  result;
    }

    public static void backTrack(List<Integer> nums, List<Integer> track,List<List<Integer>> result){
        if(nums.size() == track.size()){
            result.add(new ArrayList<>(track));
        }else{
            for(int i = 0 ; i < nums.size(); i++){
//                Integer n = nums.get(i); // choose
////                track.add(n); // 加入决策
////                nums.remove(n);// 移除这个选择
////                backTrack(nums, track, result); // 进行下一步决策
////                nums.add(n);// unchoose
////                track.remove(n);
                Integer n = nums.get(i);
                if(track.contains(n)) continue;
                track.add(n);
                backTrack(nums,track, result);
                track.remove(track.size() -1);
            }
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> result = permute(Lists.newArrayList(1,2,3,4));
        System.out.println(result.toString());
    }
}
