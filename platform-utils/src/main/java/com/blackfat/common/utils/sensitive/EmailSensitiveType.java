package com.blackfat.common.utils.sensitive;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-20 14:17
 * @since 1.0-SNAPSHOT
 */
public class EmailSensitiveType extends BaseSensitiveType {

    static final int MIN_EMAIL_LEN=6, MAX_EMAIL_LEN=64;
    static final char CHAR_AT='@', CHAR_POINT='.';

    public EmailSensitiveType() {
        super("[a-zA-Z0-9_-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,4}");
    }

    @Override
    public String name() {
        return "Email";
    }

    @Override
    public boolean match(String k, String s) {
        //长度验证
        int len = s.length();
        if(len<MIN_EMAIL_LEN || len>MAX_EMAIL_LEN){ return false; }
        //@位置验证
        int atIndex = s.indexOf(CHAR_AT);
        if(atIndex <= 0){ return false; }
        //.位置验证
        int pointIndex = s.indexOf(CHAR_POINT, atIndex+1);
        if(pointIndex < 0 || !intBetween(len-pointIndex-1, INT_2, INT_4)){ return false; }
        //@之前字符验证
        for(int i=0; i<atIndex; i++){
            char c = s.charAt(i);
            if(!charBetween(c, CHAR_a, CHAR_z) && !charBetween(c, CHAR_U_A, CHAR_U_Z) && !charBetween(c, CHAR_0, CHAR_9) && c!='_' && c!='-'){
                return false;
            }
        }
        //@～.之间字符验证
        for(int i=atIndex+1; i<pointIndex; i++){
            char c = s.charAt(i);
            if(!charBetween(c, CHAR_a, CHAR_z) && !charBetween(c, CHAR_U_A, CHAR_U_Z) && !charBetween(c, CHAR_0, CHAR_9) && c!='-'){
                return false;
            }
        }
        //.之后字符验证
        for(int i=pointIndex+1; i<len; i++){
            char c = s.charAt(i);
            if(!charBetween(c, CHAR_a, CHAR_z) && !charBetween(c, CHAR_U_A, CHAR_U_Z)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String desensitize(String s) {
        if(s == null){ return null; }
        int len = s.length();
        if(len < MIN_EMAIL_LEN){ return s; }
        int atIndex = s.indexOf(CHAR_AT);
        if(atIndex <= 0){ return s; }
        int hideSize = Math.min(INT_4, atIndex);
        return hidePart(s, atIndex-hideSize, hideSize, len-atIndex);
    }

}
