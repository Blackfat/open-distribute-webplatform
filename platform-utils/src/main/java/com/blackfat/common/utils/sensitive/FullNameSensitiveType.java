package com.blackfat.common.utils.sensitive;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-04-20 14:17
 * @since 1.0-SNAPSHOT
 */
public class FullNameSensitiveType extends BaseSensitiveType {

    public FullNameSensitiveType() {
        super(makeChineseNameRegex());
    }

    static String makeChineseNameRegex() {
        return new StringBuilder()
                .append('(').append(StringUtils.join(SURNAME_LIST, '|')).append(')')
                .append('[').append(CHAR_CN_START).append('-').append(CHAR_CN_END).append("]{1,2}")
                .toString();
    }

    @Override
    public String name() {
        return "FullName";
    }

    @Override
    public boolean match(String k, String s) {
        int len = s.length();
        //长度验证
        if(len < INT_2 || len > INT_4){ return false; }
        //中文验证
        for(int i=0; i<len; i++){
            char c = s.charAt(i);
            if(!charBetween(c, CHAR_CN_START, CHAR_CN_END)){
                return false;
            }
        }
        //姓氏验证
        char c0 = s.charAt(0);
        Object surname = SURNAME_MAP.get(c0);
        if(surname == null){ return false; }
        return true;
    }

    @Override
    public String desensitize(String s) {
        if(s == null){ return null; }
        int len = s.length();
        if(len < INT_2 || len > INT_4){ return s; }
        int hideSize = (len+1)/2;
        return hidePart(s,len-hideSize, hideSize, 0);
    }


    static final List<String> SURNAME_LIST = Arrays.asList(
            "李", "王", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴",
            "徐", "孙", "胡", "朱", "高", "林", "何", "郭", "马", "罗",
            "梁", "宋", "郑", "谢", "韩", "唐", "冯", "于", "董", "萧",
            "程", "曹", "袁", "邓", "许", "傅", "沈", "曾", "彭", "吕",
            "苏", "卢", "蒋", "蔡", "贾", "丁", "魏", "薛", "叶", "阎",
            "余", "潘", "杜", "戴", "夏", "钟", "汪", "田", "任", "姜",
            "范", "方", "石", "姚", "谭", "廖", "邹", "熊", "金", "陆",
            "郝", "孔", "白", "崔", "康", "毛", "邱", "秦", "江", "史",
            "顾", "侯", "邵", "孟", "龙", "万", "段", "漕", "钱", "汤",
            "尹", "黎", "易", "常", "武", "乔", "贺", "赖", "龚", "文",
            "庞", "樊", "兰", "殷", "施", "陶", "洪", "翟", "安", "颜",
            "倪", "严", "牛", "温", "芦", "季", "俞", "章", "鲁", "葛",
            "伍", "韦", "申", "尤", "毕", "聂", "丛", "焦", "向", "柳",
            "邢", "路", "岳", "齐", "沿", "梅", "莫", "庄", "辛", "管",
            "祝", "左", "涂", "谷", "祁", "时", "舒", "耿", "牟", "卜",
            "路", "詹", "关", "苗", "凌", "费", "纪", "靳", "盛", "童",
            "欧", "甄", "项", "曲", "成", "游", "阳", "裴", "席", "卫",
            "查", "屈", "鲍", "位", "覃", "霍", "翁", "隋", "植", "甘",
            "景", "薄", "单", "包", "司", "柏", "宁", "柯", "阮", "桂",
            "闵", "欧阳", "解", "强", "柴", "华", "车", "冉", "房", "边",
            "辜", "吉", "饶", "刁", "瞿", "戚", "丘", "古", "米", "池",
            "滕", "晋", "苑", "邬", "臧", "畅", "宫", "来", "嵺", "苟",
            "全", "褚", "廉", "简", "娄", "盖", "符", "奚", "木", "穆",
            "党", "燕", "郎", "邸", "冀", "谈", "姬", "屠", "连", "郜",
            "晏", "栾", "郁", "商", "蒙", "计", "喻", "揭", "窦", "迟",
            "宇", "敖", "糜", "鄢", "冷", "卓", "花", "仇", "艾", "蓝",
            "都", "巩", "稽", "井", "练", "仲", "乐", "虞", "卞", "封",
            "竺", "冼", "原", "官", "衣", "楚", "佟", "栗", "匡", "宗",
            "应", "台", "巫", "鞠", "僧", "桑", "荆", "谌", "银", "扬",
            "明", "沙", "薄", "伏", "岑", "习", "胥", "保", "和", "蔺"
    );

    static Map<Character, Object> initSurnameMap(){
        Map<Character, Object> map = new HashMap<>(SURNAME_LIST.size());
        for(String surname:SURNAME_LIST){
            char c0 = surname.charAt(0);
            Object val = map.get(c0);
            if(val == null){
                map.put(c0, surname);
            }else if(val instanceof List){
                ((List) val).add(surname);
            }else{
                List<String> surnames = Lists.newArrayListWithExpectedSize(2);
                surnames.add((String) val);
                surnames.add(surname);
                map.put(c0, surnames);
            }
        }
        return map;
    }

    static final Map<Character, Object> SURNAME_MAP = initSurnameMap();

}
