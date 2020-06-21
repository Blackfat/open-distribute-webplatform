package com.blackfat.common.utils.security;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-29 16:23
 * @since 1.0-SNAPSHOT
 */
public class AES {

    public static final byte keyStrSize = 16;

    final String keyAlgorithm;//加密方式 AES或者DES

    final String cipherAlgorithm;// 加密器

    protected AES(String keyAlgorithm, String cipherAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
        this.cipherAlgorithm = cipherAlgorithm;
    }

    public static AES get(String cipherAlgorithm) {
        return new AES(Algorithms.AES, cipherAlgorithm);
    }
    public static AES get(String keyAlgorithm, String cipherAlgorithm) {
        return new AES(keyAlgorithm, cipherAlgorithm);
    }

    public SecretKey generateSecret(byte[] key) throws GeneralSecurityException {
        return new SecretKeySpec(key, keyAlgorithm);
    }

    public SecretKey base64GenerateSecret(String key) throws GeneralSecurityException {
        return generateSecret(Base64.decodeBase64(key));
    }

    public byte[] decrypt(byte[] data, SecretKey secretKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public byte[] base64Decrypt(String data, SecretKey secretKey) throws GeneralSecurityException {
        return decrypt(Base64.decodeBase64(data), secretKey);
    }

    public byte[] base64Decrypt(String data, String secretKey) throws GeneralSecurityException {
        return decrypt(Base64.decodeBase64(data), base64GenerateSecret(secretKey));
    }

    public byte[] encrypt(byte[] data, SecretKey secretKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public String encryptBase64(String data, SecretKey secretKey) throws GeneralSecurityException {
        return Base64.encodeBase64String(encrypt(data.getBytes(Charsets.UTF_8), secretKey));
    }


    public String randomKey() throws GeneralSecurityException {
        return randomKey(null);
    }

    public String randomKey(String seed) throws GeneralSecurityException {
        return base64Secret(randomSecret(seed));
    }

    public String base64Secret(SecretKey secretKey){
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    public SecretKey randomSecret() throws GeneralSecurityException {
        return randomSecret(null);
    }

    /**
     * 生产密钥
     * @param seed 种子
     * @return
     * @throws GeneralSecurityException
     */
    public SecretKey randomSecret(String seed) throws GeneralSecurityException {
        KeyGenerator kg = KeyGenerator.getInstance(keyAlgorithm);
        if(seed == null){
            kg.init(128);
        }else{
            kg.init(128, new SecureRandom(Base64.decodeBase64(seed)));
        }
        return kg.generateKey();
    }

    /**
     * AES/CBC/NoPadding encrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyBytes
     * @param intVectorBytes
     * @param input
     * @return
     */
    public static byte[] encryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
        int inputLength = input.length;
        int srcLength;

        Cipher cipher = Cipher.getInstance(Algorithms.AES_CBC_NOPADDING);
        int blockSize = cipher.getBlockSize();
        byte[] srcBytes;
        if (0 != inputLength % blockSize) {
            srcLength = inputLength + (blockSize - inputLength % blockSize);
            srcBytes = new byte[srcLength];
            System.arraycopy(input, 0, srcBytes, 0, inputLength);
        } else {
            srcBytes = input;
        }

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptBytes = cipher.doFinal(srcBytes);
        return encryptBytes;
    }


    /**
     * AES/CBC/NoPadding decrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyBytes
     * @param intVectorBytes
     * @param input
     * @return
     */
    public static byte[] decryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) throws Exception {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, Algorithms.AES);

            Cipher cipher = Cipher.getInstance(Algorithms.AES_CBC_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     *
     * @param secretStr
     * @param inputStr
     * @return
     */
    public static byte[] encode(String secretStr, String inputStr) throws Exception {
        if (keyStrSize != secretStr.length()) {
            return null;
        }
        byte[] secretKeyBytes = secretStr.getBytes(Charset.forName("utf-8"));
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);
        byte[] inputBytes = inputStr.getBytes(Charset.forName("utf-8"));

        byte[] outputBytes = encryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);
        return outputBytes;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     * 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法，
     * 转换成 base64 字符串返回。
     *
     * @param secretStr
     * @param inputStr
     * @return
     */
    public static String strEncodBase64(String secretStr, String inputStr) throws Exception {
        String base64Str = Base64.encodeBase64String(encode(secretStr, inputStr));
        return base64Str;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     *
     * @param secretStr
     * @param inputBytes
     * @return
     */
    public static byte[] decode(String secretStr, byte[] inputBytes) throws Exception {
        if (keyStrSize != secretStr.length()) {
            return null;
        }
        byte[] secretKeyBytes = secretStr.getBytes(Charset.forName("utf-8"));
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);

        byte[] outputBytes = decryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);
        return outputBytes;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     * 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法，
     * 转换成 base64 字符串返回。
     *
     * （仅作为测试用途，具体加密流程以接口文档为准）
     *
     * @param secretStr
     * @param inputStr
     * @return
     * @throws IOException
     */
    public static String base64StrDecode(String secretStr, String inputStr) throws Exception {
        byte[] inputBytes;
        inputBytes = Base64.decodeBase64(inputStr);
        String outputStr = new String(decode(secretStr, inputBytes), Charset.forName("utf-8"));
        return outputStr;
    }


}
