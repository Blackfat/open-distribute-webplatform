package com.blackfat.common.utils.security;

import com.blackfat.common.utils.base.ExceptionUtil;
import com.blackfat.common.utils.bean.BeanHolder;
import com.blackfat.common.utils.bean.ThreadBeanHolder;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;

/**
 * @author wangfeiyang
 * @Description 自定义加密解密
 * @create 2020-01-02 07:35
 * @since 1.0-SNAPSHOT
 */
public class CustomCipher {

    public static final int AES_KEY_SIZE = 16;

    String securityKey;

    String version;

    byte[] signKey;

    BeanHolder<Cipher> encryptCipher;

    BeanHolder<Cipher> decryptCipher;


    public CustomCipher(String securityKey, String version) throws GeneralSecurityException {
        this.securityKey = securityKey;
        this.version = version;
        String hmacKey = SecurityUtils.md5(version, Charsets.UTF_8);
        signKey = SecurityUtils.hmacSHA256(securityKey, hmacKey.getBytes(Charsets.UTF_8));


        byte[] secretKey = SecurityUtils.hmacSHA256(securityKey, hmacKey.getBytes(Charsets.UTF_8));
        byte[] aesKey = ArrayUtils.subarray(SecurityUtils.hmacSHA256(securityKey, secretKey), 0, AES_KEY_SIZE);

        IvParameterSpec ivSpec = SecurityUtils.getIvParameterSpec(secretKey, AES_KEY_SIZE);

        encryptCipher = new ThreadBeanHolder(() -> {
            return aesEncryptCipher(aesKey, ivSpec);
        });
        decryptCipher = new ThreadBeanHolder(() -> {
            return aesDecryptCipher(aesKey, ivSpec);
        });


    }

    public static CustomCipher create(String securityKey, String version) {
        try {
            return new CustomCipher(securityKey, version);
        } catch (GeneralSecurityException e) {
            throw new ExceptionUtil.UncheckedException(e);
        }
    }

    Cipher aesEncryptCipher(byte[] aesKey, IvParameterSpec ivSpec) {
        try {
            return SecurityUtils.aesEncryptCipher(aesKey, ivSpec);
        } catch (GeneralSecurityException e) {
            throw new ExceptionUtil.UncheckedException(e);
        }
    }

    Cipher aesDecryptCipher(byte[] aesKey, IvParameterSpec ivSpec) {
        try {
            return SecurityUtils.aesDecryptCipher(aesKey, ivSpec);
        } catch (GeneralSecurityException e) {
            throw new ExceptionUtil.UncheckedException(e);
        }
    }

    public String encrypt(String plainText) throws GeneralSecurityException {
        return Base64.encodeBase64URLSafeString(encryptCipher.get().doFinal(plainText.getBytes(Charsets.UTF_8)));
    }

    public String decrypt(String cipherText) throws GeneralSecurityException {
        return new String(decryptCipher.get().doFinal(Base64.decodeBase64(cipherText)), Charsets.UTF_8);
    }


    public static void main(String[] args) throws Exception{
        CustomCipher customCipher = new CustomCipher("token.blackfat", "V1");
        String origintext = "hello world";
        System.out.println("--------------origintext------------:"+origintext);
        String ciphertext = customCipher.encrypt(origintext);
        System.out.println("--------------ciphertext------------:"+ciphertext);
        String plaintext = customCipher.decrypt(ciphertext);
        System.out.println("--------------plaintext------------:"+plaintext);

    }


}
