package com.blackfat.common.designmode.facade;

import java.io.*;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/12-14:25
 */
public class FileWriter {

    public void Write(String encryptStr,String fileNameDes)
    {
        System.out.println("保存密文，写入文件。");
        BufferedWriter bs = null;
        try
        {
            bs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileNameDes))));
            byte[] str = encryptStr.getBytes();
            bs.write(encryptStr);
            bs.flush();
            bs.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("文件不存在！");
        }
        catch(IOException e)
        {
            System.out.println("文件操作错误！");
        }
    }
}
