package com.blackfat.common.designmode.facade;

import java.io.FileInputStream;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/12-14:37
 */
public class EncryptFacade {

    private FileReader fileReader;
    private FileWriter fileWriter;
    private CipherMachine cipherMachine;

    public EncryptFacade() {
        fileReader = new FileReader();
        cipherMachine = new CipherMachine();
        fileWriter = new FileWriter();
    }

    //调用其他对象的业务方法
    public void FileEncrypt(String fileNameSrc, String fileNameDes) {
        String plainStr = fileReader.Read(fileNameSrc);
        String encryptStr = cipherMachine.Encrypt(plainStr);
        fileWriter.Write(encryptStr, fileNameDes);
    }
}

