package com.blackfat.common.utils.sensitive;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-16 19:47
 * @since 1.0-SNAPSHOT
 */
public class BankCardSensitiveType extends BaseSensitiveType {


    static final int BANK_CARD_LEN1 = 15;
    static final int BANK_CARD_LEN2 = 19;
    static final String[] IGNORE_FIELD_PARTS = {"url", "serial", "seq", "code", "batch", "arg", "json", "applyNo", "orderNo"};
    static final String[] NEED_FIELD_PARTS = {"bank", "card"};

    public BankCardSensitiveType() {
        super("[1-9](\\d{14}|\\d{18})");
    }

    @Override
    public String name() {
        return "BankCard";
    }

    @Override
    public boolean match(String k, String s) {
        //长度验证
        int len = s.length();
        if(len != BANK_CARD_LEN1 && len != BANK_CARD_LEN2){ return false; }
        //首位 1-9
        if(!charBetween(s.charAt(0), CHAR_1, CHAR_9)){ return false; }
        //第2～尾位 数字
        for(int i=1; i<len; i++){
            char c = s.charAt(i);
            if(c>CHAR_9 || c<CHAR_0){ return false; }
        }
        return true;
    }

    @Override
    public String desensitize(String s) {
        if(s == null){ return null; }
        int len = s.length();
        if(len != BANK_CARD_LEN1 && len != BANK_CARD_LEN2){ return s; }
        return hidePart(s, 6, len-10, 4);
    }
}
