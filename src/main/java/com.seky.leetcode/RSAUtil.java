package com.seky.leetcode;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.util.Base64;

public class RSAUtil {

    private static RSA RSA;

    static {
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALeEFg/tdXxjINT0gct67Xk8V1YzqxB9qehS48NOQOHSBk8CpzXe6TVAdBO7N8Qtpx9yMTgaZWf1uL0g/YL7UqvQvQQGurUDByH6hJMYqH0FehS/DY++4HBjNzLB+UYE4u07RWF1BtFzGv6O2cT+IJK7b0RMdkdyq/btb4pIr1SlAgMBAAECgYBSjWeDSLWF++2j/k145FZ5KAy9qUZ1h6Hr8YWlsMj9DIZr+myPjjPoEZNl4N0oMcUxHyRG8ZDWYDH3WiTQTBAWmM/qxUusUnpT9VAz9soU6h6UIABL3sAomC/EIiILIdX1/NE4rU6ZZAWKK/zBOcDibLTUQYGFSBwImWsJieKtTQJBAOazG1tVWTdodN7LS49WmEYgbPKngfAd2ersM86V1W/vf/MbzMU8dUDAUuDT5CX8Hbt2CFN9iM6SOEuL46F4uAMCQQDLpEwnu2GYPgQGcF6v1MHhmAk5Edm/0vq1vBRAnBs7cKDCi/PamReeA3f5KZVPb6nuA4ZNB8uOgrAgfXIk9UQ3AkAQBENT9fr5s9DZXvfh/bsYs0udFTAaQZhNjRSTtSdaXCBf+oAe+XGSi0e1wEBIutY9m5Y8ZazH2rL52750CXONAkA/OICUDS5dT1N8lEIxIEIYwVSLrSkGzn8bEIrRDCXALFUcUMqh4MleOwFyDyDVinsc82csXvEpCxWW4JshMiu9AkACJf3zPkVVbvXV/w8afUb3oRuuqWtrrfA/fZYrahPP72vi47izmxVM06mBP2mJDJqT5AXe96s/QODjkoJnle8AMIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALeEFg/tdXxjINT0gct67Xk8V1YzqxB9qehS48NOQOHSBk8CpzXe6TVAdBO7N8Qtpx9yMTgaZWf1uL0g/YL7UqvQvQQGurUDByH6hJMYqH0FehS/DY++4HBjNzLB+UYE4u07RWF1BtFzGv6O2cT+IJK7b0RMdkdyq/btb4pIr1SlAgMBAAECgYBSjWeDSLWF++2j/k145FZ5KAy9qUZ1h6Hr8YWlsMj9DIZr+myPjjPoEZNl4N0oMcUxHyRG8ZDWYDH3WiTQTBAWmM/qxUusUnpT9VAz9soU6h6UIABL3sAomC/EIiILIdX1/NE4rU6ZZAWKK/zBOcDibLTUQYGFSBwImWsJieKtTQJBAOazG1tVWTdodN7LS49WmEYgbPKngfAd2ersM86V1W/vf/MbzMU8dUDAUuDT5CX8Hbt2CFN9iM6SOEuL46F4uAMCQQDLpEwnu2GYPgQGcF6v1MHhmAk5Edm/0vq1vBRAnBs7cKDCi/PamReeA3f5KZVPb6nuA4ZNB8uOgrAgfXIk9UQ3AkAQBENT9fr5s9DZXvfh/bsYs0udFTAaQZhNjRSTtSdaXCBf+oAe+XGSi0e1wEBIutY9m5Y8ZazH2rL52750CXONAkA/OICUDS5dT1N8lEIxIEIYwVSLrSkGzn8bEIrRDCXALFUcUMqh4MleOwFyDyDVinsc82csXvEpCxWW4JshMiu9AkACJf3zPkVVbvXV/w8afUb3oRuuqWtrrfA/fZYrahPP72vi47izmxVM06mBP2mJDJqT5AXe96s/QODjkoJnle8A";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3hBYP7XV8YyDU9IHLeu15PFdWM6sQfanoUuPDTkDh0gZPAqc13uk1QHQTuzfELacfcjE4GmVn9bi9IP2C+1Kr0L0EBrq1Awch+oSTGKh9BXoUvw2PvuBwYzcywflGBOLtO0VhdQbRcxr+jtnE/iCSu29ETHZHcqv27W+KSK9UpQIDAQAB";
        RSA = new RSA(privateKey, publicKey);
    }

    /**
     * 加密
     */
    public static String encrypt(String c) {
        String r = new String(Base64.getEncoder().encode(RSA.encrypt(StrUtil.bytes(c, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey)));
        return r;
    }

    /**
     * 解密
     */
    public static String decode(String c) {
        return new String(RSA.decrypt(Base64.getDecoder().decode(c.getBytes()), KeyType.PrivateKey));
    }

    public static void main(String[] args) {
        String plaintext = decode("fcR3zknYEb3kLNT+E/K29GwqSiCJJ6Btq4xzJqB4WGqERV1ZEoF312EYebR6a6Yksf1WGwcI2sEOU2iii2eUt/puxiXP6H5Bew+a4N2Exx1DKeKCj1WWoHX8GO5K9he53cU23/Tb9l8OZx31QiB/7ledFGhvwqOhTuoMtTyDExg=");
        System.out.println("plaintext = " + plaintext);

        String content = "2020-10";

        String ciphertext = encrypt(content);
        System.out.println("ciphertext = " + ciphertext);
        
        String plaintext1 = decode(ciphertext);
        System.out.println("plaintext1 = " + plaintext1);
    }
}