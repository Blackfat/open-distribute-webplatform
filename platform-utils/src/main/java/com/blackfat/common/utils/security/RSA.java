package com.blackfat.common.utils.security;

import com.blackfat.common.utils.io.IOUtil;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-07-29 16:25
 * @since 1.0-SNAPSHOT
 */
public class RSA {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public final String keyAlgorithm;
    public final String signAlgorithm;

    private RSA(String keyAlgorithm, String signAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
        this.signAlgorithm = signAlgorithm;
    }

    public static RSA get(String keyAlgorithm, String signAlgorithm) {
        return new RSA(keyAlgorithm, signAlgorithm);
    }

    public PublicKey generatePublicKey(String pubKey) throws GeneralSecurityException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(pubKey));
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithms.RSA);
        return keyFactory.generatePublic(keySpec);
    }

    public PrivateKey generatePrivateKey(String priKey) throws GeneralSecurityException {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(priKey));
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithms.RSA);
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    public boolean verify(byte[] data, PublicKey pubKey, String sign) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    public boolean verify(String data, PublicKey pubKey, String sign) throws GeneralSecurityException {
        return verify(data.getBytes(Charsets.UTF_8), pubKey, sign);
    }

    public boolean verify(String data, String pubKey, String sign) throws GeneralSecurityException {
        PublicKey publicKey = generatePublicKey(pubKey);
        return verify(data.getBytes(Charsets.UTF_8), publicKey, sign);
    }

    public String sign(byte[] data, PrivateKey priKey) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(priKey);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    public String sign(String data, PrivateKey priKey) throws GeneralSecurityException {
        return sign(data.getBytes(Charsets.UTF_8), priKey);
    }

    public byte[] base64Decrypt(String data, Key key) throws GeneralSecurityException {
        return decrypt(Base64.decodeBase64(data), key);
    }

    public String encryptBase64(String data, Key key) throws GeneralSecurityException {
        return encryptBase64(data.getBytes(Charsets.UTF_8), key);
    }

    public String encryptBase64(byte[] data, Key key) throws GeneralSecurityException {
        return Base64.encodeBase64String(encrypt(data, key));
    }

    public byte[] encrypt(byte[] data, Key key) throws GeneralSecurityException {
        return doFinal(data, Cipher.ENCRYPT_MODE, key);
    }

    public byte[] decrypt(byte[] data, Key key) throws GeneralSecurityException {
        return doFinal(data, Cipher.DECRYPT_MODE, key);
    }

    byte[] doFinal(byte[] data, int cipherMode, Key key) throws GeneralSecurityException {
        int step = getFinalStep(cipherMode, key);
        Cipher cipher = getCipher(cipherMode, key);
        if (data.length > step) {
            return doFinalSteps(data, cipher, step);
        } else {
            return cipher.doFinal(data);
        }
    }

    byte[] doFinalSteps(byte[] data, Cipher cipher, int step) throws GeneralSecurityException {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int len = data.length, offset = 0;
            for (int i = 0; len - offset > 0; offset = i * step) {
                byte[] cache;
                if (len - offset > step) {
                    cache = cipher.doFinal(data, offset, step);
                } else {
                    cache = cipher.doFinal(data, offset, len - offset);
                }
                out.write(cache, 0, cache.length);
                ++i;
            }
            return out.toByteArray();
        } finally {
            IOUtil.closeQuietly(out);
        }
    }

    public static int getKeySize(Key rsaKey) {
        return ((RSAKey) rsaKey).getModulus().bitLength();
    }

    int getFinalStep(int cipherMode, Key key) {
        int step = getKeySize(key) / 8;
        if (cipherMode == Cipher.ENCRYPT_MODE) {
            return step - 11;
        }
        return step;
    }

    Cipher getCipher(int cipherMode, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(keyAlgorithm);
        cipher.init(cipherMode, key);
        return cipher;
    }

    public KeyPair generateKeyPair() throws GeneralSecurityException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(Algorithms.RSA);
        keyPairGen.initialize(1024);
        return keyPairGen.generateKeyPair();
    }

    /**
     * <P>
     * 公钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {

        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithms.RSA);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param privateKey
     *            私钥(BASE64编码)
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {

        byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithms.RSA);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(Algorithms.RSA);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<>(2);

        keyMap.put("publicKey", Base64.encodeBase64String(publicKey.getEncoded()));
        keyMap.put("privateKey", Base64.encodeBase64String(privateKey.getEncoded()));
        return keyMap;
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes =  new BASE64Decoder().decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithms.RSA);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithms.RSA);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
}
