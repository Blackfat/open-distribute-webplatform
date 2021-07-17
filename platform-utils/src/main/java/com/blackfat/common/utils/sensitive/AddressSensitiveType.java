package com.blackfat.common.utils.sensitive;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-20 14:15
 * @since 1.0-SNAPSHOT
 */
public class AddressSensitiveType extends BaseSensitiveType {

    static final int MIN_ADDRESS_LEN=6, MAX_ADDRESS_LEN=128;
    static final char[] ADDRESS_FIRST_UNIT_CHARS = {'号', '弄', '楼', '栋', '#'};
    static final char[] ADDRESS_LAST_UNIT_CHARS = {'号', '室', '户'};

    static final char[] ADDRESS_CN_NUM_CHARS = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    public AddressSensitiveType() {
        super(makeAddressRegex());
    }

    static String makeAddressRegex() {
        return new StringBuilder()
                .append("[\u4E00-\u9FA5]{2,}[0-9a-zA-Z-]+")
                .append('(').append(StringUtils.join(ADDRESS_FIRST_UNIT_CHARS, '|')).append(')')
                .append("([0-9a-zA-Z\u4E00-\u9FA5-#]+[0-9").append(new String(ADDRESS_LAST_UNIT_CHARS)).append("])?")
                .toString();
    }

    @Override
    public String name() {
        return "Address";
    }

    @Override
    public boolean match(String k, String s) {
        int len = s.length();
        //长度验证
        if(len<MIN_ADDRESS_LEN || len>MAX_ADDRESS_LEN){ return false; }
        //首个单位验证
        int firstUnitIdx = StringUtils.indexOfAny(s, ADDRESS_FIRST_UNIT_CHARS);
        if(firstUnitIdx < INT_3){ return false; }
        //首个单位前缀验证
        int i = 0;
        for(; i<firstUnitIdx; i++){
            char c = s.charAt(i);
            if(!charBetween(c, CHAR_CN_START, CHAR_CN_END)){
                break;
            }
        }
        //首个单位前缀长度判定
        if(i < INT_2){ return false; }
        //首个单位值长度判定
        if(i >= firstUnitIdx){ return false; }
        //首个单位值验证
        for(; i<firstUnitIdx; i++){
            char c = s.charAt(i);
            if(!charBetween(c, CHAR_a, CHAR_z) && !charBetween(c, CHAR_U_A, CHAR_U_Z) && !charBetween(c, CHAR_0, CHAR_9) && charBetween(c, CHAR_CN_START, CHAR_CN_END) && c!='-'){
                return false;
            }
        }
        //是否有后续
        int len_1 = len - 1;
        if(firstUnitIdx == len_1){
            return true;
        }
        //尾位验证
        char last = s.charAt(len_1);
        if(!charBetween(last, CHAR_0, CHAR_9) && !ArrayUtils.contains(ADDRESS_LAST_UNIT_CHARS, last)){
            return false;
        }
        //尾位前缀验证
        for(i=firstUnitIdx+1; i<len_1; i++){
            char c = s.charAt(i);
            if(!charBetween(c, CHAR_a, CHAR_z) && !charBetween(c, CHAR_U_A, CHAR_U_Z) && !charBetween(c, CHAR_0, CHAR_9) && !charBetween(c, CHAR_CN_START, CHAR_CN_END) && c!='-'){
                return false;
            }
        }
        return true;
    }

    @Override
    public String desensitize(String s) {
        if(s == null){ return null; }
        int len = s.length();
        if(len<MIN_ADDRESS_LEN || len>MAX_ADDRESS_LEN){ return s; }
        StringBuilder sb = new StringBuilder(len);
        for(int i=0; i<len; i++){
            char c = s.charAt(i);
            if(charBetween(c, CHAR_0, CHAR_9) || ArrayUtils.contains(ADDRESS_CN_NUM_CHARS, c)){
                sb.append('*');
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
