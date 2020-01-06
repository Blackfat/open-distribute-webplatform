package com.blackfat.common.utils.security;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

    public static String md5(String text, Charset charset) throws NoSuchAlgorithmException {
        byte[] input = text.getBytes(charset);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(input);
        StringBuilder sb = new StringBuilder();
        for(byte b:hash){
            int val = ((int) b) & 0xff;
            if (val < 16){ sb.append('0'); }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }

    public static byte[] hmacSHA256(String message, byte[] key) throws GeneralSecurityException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secret);
        return mac.doFinal(message.getBytes());
    }

    public static byte[] hmacSHA1(String message, byte[] key) throws GeneralSecurityException {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(key, "HmacSHA1");
        mac.init(secret);
        return mac.doFinal(message.getBytes());
    }

    public static String hexHmacSHA256(String message, byte[] key) throws GeneralSecurityException {
        return Hex.encodeHexString(hmacSHA256(message, key));
    }

    public static String base64HmacSHA256(String message, byte[] key) throws GeneralSecurityException {
        return Base64.encodeBase64String(hmacSHA256(message, key));
    }


    public static Cipher aesEncryptCipher(byte[] aeskey, IvParameterSpec ivSpec) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(aeskey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        return cipher;
    }

    public static Cipher aesDecryptCipher(byte[] aeskey, IvParameterSpec ivSpec) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(aeskey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return cipher;
    }

    public static IvParameterSpec getIvParameterSpec(byte[] ivKey, int size){
        byte[] icv = new byte[size];
        System.arraycopy(ivKey, 0, icv, 0, size);
        return new IvParameterSpec(icv);
    }

    public static void main(String[] args) throws Exception {
        byte[] k = md5("1234567890", Charsets.UTF_8).getBytes();
        String s = "https://www.baidu.com/s?wd=HmacSHA256%20HmacSHA1%20%E6%AF%94%E8%BE%83&rsv_spt=1&rsv_iqid=0x86c8cc0800080655&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=0&oq=HmacSHA256%2520HmacSHA1&rsv_t=4082UQzWn%2BtTkIcdlKPb2T8udA0Sw3t8zcIFmIMF%2BIo%2BdiRYYA6MM6vSzdD0xzPrVCac&inputT=4215&rsv_pq=cba708fa0018e308&rsv_sug3=138&rsv_sug1=93&rsv_sug7=000&rsv_sug2=0&rsv_sug4=5610&rsv_sug=1";
        long t1 = System.currentTimeMillis();

        for(int i=0;i<100000;i++){
            hmacSHA1(s, k);
        }
        long t2 = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            hmacSHA256(s, k);
        }
        long t3 = System.currentTimeMillis();
        System.out.println(t2-t1);
        System.out.println(t3-t2);
    }
}
