package com.blackfat.common.utils.sensitive;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-20 14:19
 * @since 1.0-SNAPSHOT
 */
public class IDCardSensitiveType extends BaseSensitiveType  {

    static final int ID_CARD_LEN = 18;
    static final String STR_01="01", MAX_MONTH="12", MAX_DAY="31";


    public IDCardSensitiveType() {
        super("[1-9]\\d{5}(18|19|(2\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]");
    }

    @Override
    public String name() {
        return "IDCard";
    }

    @Override
    public boolean match(String k, String s) {
        //长度验证
        if(s.length() != ID_CARD_LEN){ return false; }
        //首位 1-9
        if(!charBetween(s.charAt(0), CHAR_1, CHAR_9)){ return false; }
        //第2～6位 数字
        if(!subStringNumeric(s, 1, INT_6)){ return false; }
        //第7～10位 年 支持1800～2999
        if(!charBetween(s.charAt(INT_6), CHAR_1, CHAR_2)){ return false; }
        if(!subStringNumeric(s, INT_7, INT_10)){ return false; }
        //第11～12位 月
        if(!stringBetween(s.substring(INT_10, INT_12), STR_01, MAX_MONTH)){ return false; }
        //第13～14位 日
        if(!stringBetween(s.substring(INT_12, INT_14), STR_01, MAX_DAY)){ return false; }
        //第15～17位 数字
        if(!subStringNumeric(s, INT_14, INT_17)){ return false; }
        //第18位 0-9、X、x
        char char17 = s.charAt(INT_17);
        return charBetween(char17, CHAR_0, CHAR_9) || char17=='x' || char17=='X';
    }

    @Override
    public String desensitize(String s) {
        if(s == null){ return null; }
        if(s.length() != ID_CARD_LEN){ return s; }
        return hidePart(s, 6, 8, 4);
    }
}
