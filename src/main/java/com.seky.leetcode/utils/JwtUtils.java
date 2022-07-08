package com.seky.leetcode.utils;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.AlgorithmUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;

import java.security.KeyPair;
import java.util.Base64;
import java.util.Date;

/**
 * @author: wf
 * @create: 2022/5/27 10:24
 * @description:
 */
public class JwtUtils {

    /**
     * 对称加密--密钥
     */
    private static String SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN1I0ZjY=";


    /**
     * 对称加密: 创建token
     *
     * @return
     */
    public static String createTokenBySym() {
        long currentTime = System.currentTimeMillis();
        long expires = currentTime + (1000 * 10);

        //密钥
        byte[] key = Base64.getDecoder().decode(SECRET);
        String token = JWT.create()
                //默认算法是：HS256
                .setHeader(JWTHeader.ALGORITHM, "HS256")
                .setHeader(JWTHeader.CONTENT_TYPE, "非对称加密")
                .setHeader(JWTHeader.TYPE, "jwt")
                .setHeader(JWTHeader.KEY_ID, 121312)
                .setPayload("userId", 100)
                .setPayload("userName", "wangfei1")
                //签发时间 和 过期时间
                .setIssuedAt(new Date(currentTime))
                .setExpiresAt(new Date(expires))
                //秘钥
                .setKey(key)
                .sign();

        return token;
    }

    /**
     * 对称加密: 验证token
     *
     * @param token
     * @return
     */
    public static boolean checkTokenBySym(String token) {
        try {
            //密钥
            byte[] key = Base64.getDecoder().decode(SECRET);
            JWT jwt = JWT.of(token).setKey(key);
            //校验时间是否有效, 过期抛异常
            JWTValidator.of(jwt).validateDate();

            System.out.println(jwt.getHeader());
            System.out.println(jwt.getHeader(JWTHeader.ALGORITHM));
            System.out.println(jwt.getHeader().getClaim(JWTHeader.ALGORITHM));
            System.out.println("=================================================");
            System.out.println(jwt.getPayload());
            System.out.println(jwt.getPayload("userName"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public static String getJwtToken() {
        long timeMillis = System.currentTimeMillis();
        long expires = timeMillis + (1000 * 10);

        String id = "RS256";
        //非对称加密: 随机生成密钥对，此处用户可自行读取`KeyPair`、公钥或私钥生成`JWTSigner`
        KeyPair keyPair = KeyUtil.generateKeyPair(AlgorithmUtil.getAlgorithm(id));
        JWTSigner signer = JWTSignerUtil.createSigner(id, keyPair);

        String token = JWT.create()
                .setHeader(JWTHeader.ALGORITHM, "RS256")
                .setHeader(JWTHeader.CONTENT_TYPE, "非对称加密")
                .setHeader(JWTHeader.TYPE, "jwt")
                .setHeader(JWTHeader.KEY_ID, 121312)
                .setPayload("userId", 100)
                .setPayload("userName", "wangfei1")
                //签发时间 和 过期时间
                .setIssuedAt(new Date(timeMillis))
                .setExpiresAt(new Date(expires))
                //签名器
                .setSigner(signer)
                .sign();

        System.out.println(token);

        //非对称加密时,加密 和 解密需要同一个signer
        JWT jwt = JWT.of(token).setSigner(signer);
        //JWT.verify，只能验证JWT Token的签名是否有效
        System.out.println(jwt.verify());

        //JWTValidator校验过期时间
        System.out.println(JWTValidator.of(jwt).validateAlgorithm(signer).validateDate());

        System.out.println(jwt.getHeader());
        System.out.println(jwt.getHeader(JWTHeader.ALGORITHM));
        System.out.println("====================================");
        System.out.println(jwt.getPayload());
        System.out.println(jwt.getPayload("userName"));
        
        return token;
    }


    public static void main(String[] args) {
        //对称加密
        String token = createTokenBySym();
        System.out.println(checkTokenBySym(token));
        
        //非对称加密
        getJwtToken();
    }
}
