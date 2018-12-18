package com.blackfat.common.algorithm;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/12/14-17:07
 */
public class StackCase {


    public static boolean isVaild(String s){
        if(StringUtils.isEmpty(s)){
            return false;
        }
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }else if (c == ')' || c == ']' || c == '}'){
                if(stack.isEmpty()){
                    return false;
                }
                char topChar = stack.pop();
                if (c == ')' && topChar != '(') {
                    return false;
                }
                if (c == ']' && topChar != '[') {
                    return false;
                }
                if (c == '}' && topChar != '{') {
                    return false;
                }
            }

        }
        return stack.empty();


    }





}
