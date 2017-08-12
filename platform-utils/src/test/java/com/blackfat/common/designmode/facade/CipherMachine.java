package com.blackfat.common.designmode.facade;

import java.io.Console;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/12-14:23
 */
public class CipherMachine {

    public String Encrypt(String plainText)
    {
        System.out.println("数据加密，将明文转换为密文：");
        String es = "";
        char[] chars = plainText.toCharArray();
        for(char ch : chars)
        {
            String c = String.valueOf(ch % 7);
            es += c;
        }
        System.out.println(es);
        return es;
    }
}
