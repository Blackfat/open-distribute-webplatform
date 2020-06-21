package com.blackfat.common.utils.security;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wangfeiyang
 * @Description 数据脱敏
 * @create 2020-05-28 16:55
 * @since 1.0-SNAPSHOT
 */
public class DesensitizationUtils {

    /**
     * 脱敏身份证
     * @param idCard
     * @return
     */
    private static String idCard(String idCard) {
        String num = StringUtils.right(idCard, 4);
        return StringUtils.leftPad(num, StringUtils.length(idCard), "*");
    }


    /**
     * 脱敏姓名
     * @param chineseName
     * @return
     */
    public static String chineseName(String chineseName) {
        String name = StringUtils.left(chineseName, 1);
        return StringUtils.rightPad(name, StringUtils.length(chineseName), "*");
    }
}
