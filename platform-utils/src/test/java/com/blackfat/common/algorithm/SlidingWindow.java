package com.blackfat.common.algorithm;

import java.util.HashMap;

/**
 * @author wangfeiyang
 * @Description leetcode 76 567 3
 * @create 2021-03-31 13:40
 * @since 1.0-SNAPSHOT
 */
public class SlidingWindow {

    private HashMap<Character, Integer> need = new HashMap<>();
    private HashMap<Character, Integer> window = new HashMap<>();

    public String slidingWindow(String s, String t) {
        int tLen = t.length();//目标字符串
        int sLen = s.length();
        if (tLen == 0 || sLen == 0) return "";

        //先把t中的字符放到need表中，计数
        for (int i = 0; i < tLen; i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;
        // 记录最小覆盖子串的起始索引及长度
        int len = Integer.MAX_VALUE, start = 0;
        int valid = 0; //已经匹配成功的字符种类数（非字符个数）


        while (right < sLen) {
            char c = s.charAt(right);
            right++;//右指针向右滑

            //如果右指针现在滑到的字符是目标字符串的一个，那么更新窗口中的数据
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            //窗口开始从左边收缩
            while (valid == need.size()) {
                // 在这里更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d 是将移出窗口的字符
                char d = s.charAt(left);
                left++;
                // 进行窗口内数据的一系列更新
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    // 判断 s 中是否存在 t 的排列
    public boolean checkInclusion(String s, String t){
        int tLen = t.length();
        int sLen = s.length();
        if (tLen == 0 || sLen == 0) return false;

        //先把t中的字符放到need表中，计数
        for (int i = 0; i < tLen; i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;
        int valid = 0;


        while(right < sLen){
            char c = s.charAt(right);
            right++;//右指针向右滑
            // 进行窗口内数据的一系列更新
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c)))
                    valid++;
            }
            // 判断左侧窗口是否要收缩
            while (right - left >= tLen) {
                // 在这里判断是否找到了合法的子串
                if (valid == need.size())
                    return true;
                char d = s.charAt(left);
                left++;
                // 进行窗口内数据的一系列更新
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d)))
                        valid--;
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }

        }
        return  false;
    }


    public Integer lengthOfLongestSubstring(String s){
        int sLen = s.length();
        if(sLen == 0){
            return  0;
        }
        int left = 0, right = 0;
        int res = 0;
        while(right < sLen){
            char c = s.charAt(right);
            right++;//右指针向右滑
            window.put(c, window.getOrDefault(c, 0) + 1);
            while(window.get(c) > 1){
                char d = s.charAt(left);
                left++;
                window.put(d, window.getOrDefault(c, 0) - 1);
            }
            res =Math.max(res, right -left);
        }
        return  res;
    }

    public static void main(String[] args) {
        SlidingWindow slidingWindow = new SlidingWindow();
//        String s = "EBBANCF";
//        String t = "ABC";
//        System.out.println(slidingWindow.slidingWindow(s, t));
        String s1 = "eidboaoo";
        String t1 = "ab";
        System.out.println(slidingWindow.checkInclusion(s1, t1));
        System.out.println(slidingWindow.lengthOfLongestSubstring("abcabcbb"));
    }

}
