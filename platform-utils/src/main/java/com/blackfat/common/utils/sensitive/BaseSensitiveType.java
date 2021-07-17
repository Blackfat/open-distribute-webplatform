package com.blackfat.common.utils.sensitive;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-16 19:44
 * @since 1.0-SNAPSHOT
 */
public abstract class BaseSensitiveType implements SensitiveType {
    static final char CHAR_0='0', CHAR_1='1', CHAR_2='2', CHAR_3='3', CHAR_9='9';
    static final int INT_2=2, INT_3=3, INT_4=4, INT_6=6, INT_7=7, INT_10=10, INT_12=12, INT_14=14, INT_17=17, INT_64=64;
    static final char CHAR_a='a', CHAR_z='z', CHAR_U_A='A', CHAR_U_Z='Z';
    static final char CHAR_CN_START='\u4E00', CHAR_CN_END='\u9FA5';
    static final String ID="id", ____CDATA_="><![CDATA[";

    Pattern pattern;

    public BaseSensitiveType(String... regex) {
        this.pattern = unionCompile(regex);
    }


    @Override
    public boolean match(String k, String s) {
        return pattern.matcher(s).matches();
    }



    String hidePart(String s, int leftSize, int hideSize, int rightSize){
        StringBuilder sb = new StringBuilder(leftSize+hideSize+rightSize);
        int length = s.length();
        for(int i=0; i< leftSize; i++){
            sb.append(s.charAt(i));
        }
        for(int i=0; i< hideSize; i++){
            sb.append('*');
        }
        for(int i=rightSize; i>0; i--){
            sb.append(s.charAt(length - i));
        }
        return sb.toString();
    }

    Pattern unionCompile(String... regex){
        if(ArrayUtils.isEmpty(regex)){ return null; }
        String unionRegex = null;
        if(regex.length > 1){
            for (int i=0; i<regex.length; i++){
                regex[i] = '('+regex[i]+')';
            }
            unionRegex = StringUtils.join(regex, '|');
        }else{
            unionRegex = regex[0];
        }
        return Pattern.compile(unionRegex);
    }

    boolean containsIgnoreCaseAny(String s, String[] parts){
        for(String part:parts){
            if(StringUtils.containsIgnoreCase(s, part)){
                return true;
            }
        }
        return false;
    }

    boolean endsWithIgnoreCaseAny(String s, String[] parts){
        for(String part:parts){
            if(StringUtils.endsWithIgnoreCase(s, part)){
                return true;
            }
        }
        return false;
    }

    boolean charBetween(char c, char left, char right){
        if(c>right || c<left){ return false; }
        return true;
    }

    boolean intBetween(int i, int left, int right){
        if(i>right || i<left){ return false; }
        return true;
    }

    boolean stringBetween(String s, String left, String right){
        if(s.compareTo(right)>0 || s.compareTo(left)<0){ return false; }
        return true;
    }

    boolean subStringNumeric(String s, int start, int end){
        for(int i=start; i<end; i++){
            if(!charBetween(s.charAt(i), CHAR_0, CHAR_9)){
                return false;
            }
        }
        return true;
    }



}
