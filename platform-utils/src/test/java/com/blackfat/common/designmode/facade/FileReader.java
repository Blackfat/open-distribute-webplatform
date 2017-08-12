package com.blackfat.common.designmode.facade;

import java.io.*;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/12-14:17
 */
public class FileReader {

    public String Read(String fileNameSrc)
    {
        System.out.println("读取文件，获取明文：");
        FileInputStream fs = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            fs = new FileInputStream(new File(fileNameSrc));
            int data;
            while((data = fs.read())!= -1)
            {
                sb = sb.append((char)data);
            }
            fs.close();
            System.out.println(sb.toString());
        }
        catch(FileNotFoundException e)
        {
            System.out.println("文件不存在！");
        }
        catch(IOException e)
        {
            System.out.println("文件操作错误！");
        }
        return sb.toString();
    }

}
