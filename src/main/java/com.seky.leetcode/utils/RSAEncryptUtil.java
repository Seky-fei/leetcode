package com.seky.leetcode.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author: wf
 * @create: 2022/1/13 16:31
 * @description:  RSA非对称加密(公钥加密 -- 私钥解密, 私钥加密 -- 公钥解密)
 */
@Slf4j
public class RSAEncryptUtil {
    /**
     * 私钥
     */
    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJVphj1ciHjQ+tBmXxg95Mkx0ZHzxN/AGOtxShN0y8hGHr8g2Q2mlEq7Vj" +
            "y4O2U7+O3NoiifZQDkSra9P/nRBEPZYf0VNCh1jWwIfcFR5ASFNYe1eR1/AgJtJq0sqGgDUDbYM2euTrWXxl+x9OZ+XR4Ynk1NW43/9S3Hh627Z8hR" +
            "AgMBAAECgYAjJUdmd7XCdaYKD4qe8TA4A3gAlQ7icxVpjKSYHwkwew4Qd3GXPG/1hEuszMlr7seHaK+UTqo7o3fPtISwBvKxho4WHPJs29Vhv+iagq0QCO" +
            "FDggY79Zauw6xEaThEtPazIk+b1MF2o7l/nY0urSjYLC9yHHuPPglwtY194TRgAQJBANnIIchTHh8ZMpX4pKngbQRJ8UcPOq1Rl1VPth28WWmOfGa9DqPIyI" +
            "SVLTvlMWo8pkCtl7MXDKc+6/WLhktzeRECQQCvoeIGdc0fjNdzJSeg2Ob7QKvD8H7aac2gwbU0+NhPtAsQ1eWn3Hy7Pm0yOowfS0oxWp5J7sF8b94zQqPOPVt" +
            "BAkEAk/s9mVWBPjhs+yL9IMFy0ls8K8DZajPq1PlZElONngBH37fPXtNsDUsgdXaDYWDcx04tqm+bFXnX8/1ev54zQQJAHF0we4qazfWY9eeSNs/QvdL76nD0" +
            "i6F6q/OcqCloBbc18koZBLr/Cd5TFEGQT52BJedUmOwOI2KNqAmjGrOeAQJAU0FrFzE/HTXmyLkEMmvYr3ktF7IRDMGQGGnXUbHOmVDhU44GHVygJhe6fGqZ5ie++xyGyRR16i2ZU1Egb7kaNg==";
    
    /**
     * 公钥
     */
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVaYY9XIh40PrQZl8YPeTJMdGR88TfwBjrcUoTdMvIRh6/INkNppRKu1Y8uDtlO/jtzaIon2UA5Eq2vT/50QRD2WH9FTQodY1sCH3BUeQEhTWHtXkdfwICbSatLKhoA1A22DNnrk61l8ZfsfTmfl0eGJ5NTVuN//Utx4etu2fIUQIDAQAB";
    
    
    private static final String KEY_ALGORITHM = "RSA";
    private static final int RSA_KEY_SIZE = 1024;
    public final static String UTF8 = "utf-8";
    
    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(RSA_KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        System.out.println("publicKey = " + publicKeyString);
        System.out.println("privateKey = " + privateKeyString);
    }
    
    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) {
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(UTF8)));
            return outStr;
        } catch (Exception e) {
            log.info("RSA公钥加密发生异常, 异常信息：", e);
            return null;
        }
    }
    
    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes(UTF8));
            // base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            // RSA解密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        } catch (Exception e) {
            log.info("RSA私钥解密发生异常, 异常信息：", e);
            return null;
        }
    }
    
    /**
     * 私钥加密
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     */
    public static String encryptByPrivateKey(String data, String privateKey) {
        try {
            byte[] kb = Base64.decodeBase64(privateKey.getBytes(UTF8));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(kb));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypt = cipher.doFinal(data.getBytes(UTF8));
            return Base64.encodeBase64String(encrypt);
        } catch (Exception e) {
            log.info("RSA私钥加密发生异常, 异常信息：", e);
            throw new RuntimeException();
        }
    }
    
    /**
     * 公钥解密
     *
     * @param data      解密数据
     * @param publicKey 公钥
     * @return
     */
    public static String decryptByPublicKey(String data, String publicKey) {
        try {
            byte[] kb = Base64.decodeBase64(publicKey.getBytes(UTF8));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(new X509EncodedKeySpec(kb));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypt = cipher.doFinal(Base64.decodeBase64(data.getBytes(UTF8)));
            return new String(decrypt, UTF8);
        } catch (Exception e) {
            log.info("RSA公钥解密发生异常, 异常信息：", e);
            return null;
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        RSAEncryptUtil.genKeyPair();
        System.out.println("====================================================");
        //公钥加密 -- 私钥解密
        String cipherText = RSAEncryptUtil.encrypt("哈哈哈哈", PUBLIC_KEY);
        System.out.println("密文 " + cipherText);
        String decrypt = RSAEncryptUtil.decrypt(cipherText, PRIVATE_KEY);
        System.out.println(decrypt);
        System.out.println("====================================================");
        
        //私钥加密 -- 公钥解密
        String cipherText1 = RSAEncryptUtil.encryptByPrivateKey("哈哈哈哈", PRIVATE_KEY);
        System.out.println("私钥加密后密文 " + cipherText1);
        String decrypt1 = RSAEncryptUtil.decryptByPublicKey(cipherText1, PUBLIC_KEY);
        System.out.println(decrypt1);
    }
}
