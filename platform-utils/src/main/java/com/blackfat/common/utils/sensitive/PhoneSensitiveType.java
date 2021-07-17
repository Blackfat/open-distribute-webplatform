package com.blackfat.common.utils.sensitive;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-20 14:19
 * @since 1.0-SNAPSHOT
 */
public class PhoneSensitiveType extends BaseSensitiveType {

    static final int PHONE_LEN = 11;
    static final String[] IGNORE_FIELD_PARTS = {"url", "serial", "seq", "code", "size", "applyNo", "orderNo"};

    public PhoneSensitiveType() {
        super("1[3-9]\\d{9}");
    }

    @Override
    public String name() {
        return "Phone";
    }

    @Override
    public boolean match(String k, String s) {
        //长度验证
        if(s.length() != PHONE_LEN){ return false; }
        //首位 1
        if(s.charAt(0) != CHAR_1){ return false; }
        //第2位 3-9
        if(!charBetween(s.charAt(1), CHAR_3, CHAR_9)){ return false; }
        //第3～11位 数字
        for(int i=2; i<PHONE_LEN; i++){
            char c = s.charAt(i);
            if(c>CHAR_9 || c<CHAR_0){ return false; }
        }
        return true;
    }

    @Override
    public String desensitize(String s) {
        if(s == null){ return null; }
        if(s.length() != PHONE_LEN){ return s; }
        return hidePart(s, 3, 4, 4);
    }
}
