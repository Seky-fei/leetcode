package com.seky.leetcode;

import cn.hutool.crypto.asymmetric.RSA;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: wf
 * @create: 2021/8/26 10:57
 * @description:
 */
public class MyLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final Integer MAXIMUM_CAPACITY = 5;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    public MyLinkedHashMap() {
        super(MAXIMUM_CAPACITY, DEFAULT_LOAD_FACTOR, true);
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        if(this.size() > MAXIMUM_CAPACITY){
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALeEFg/tdXxjINT0gct67Xk8V1YzqxB9qehS48NOQOHSBk8CpzXe6TVAdBO7N8Qtpx9yMTgaZWf1uL0g/YL7UqvQvQQGurUDByH6hJMYqH0FehS/DY++4HBjNzLB+UYE4u07RWF1BtFzGv6O2cT+IJK7b0RMdkdyq/btb4pIr1SlAgMBAAECgYBSjWeDSLWF++2j/k145FZ5KAy9qUZ1h6Hr8YWlsMj9DIZr+myPjjPoEZNl4N0oMcUxHyRG8ZDWYDH3WiTQTBAWmM/qxUusUnpT9VAz9soU6h6UIABL3sAomC/EIiILIdX1/NE4rU6ZZAWKK/zBOcDibLTUQYGFSBwImWsJieKtTQJBAOazG1tVWTdodN7LS49WmEYgbPKngfAd2ersM86V1W/vf/MbzMU8dUDAUuDT5CX8Hbt2CFN9iM6SOEuL46F4uAMCQQDLpEwnu2GYPgQGcF6v1MHhmAk5Edm/0vq1vBRAnBs7cKDCi/PamReeA3f5KZVPb6nuA4ZNB8uOgrAgfXIk9UQ3AkAQBENT9fr5s9DZXvfh/bsYs0udFTAaQZhNjRSTtSdaXCBf+oAe+XGSi0e1wEBIutY9m5Y8ZazH2rL52750CXONAkA/OICUDS5dT1N8lEIxIEIYwVSLrSkGzn8bEIrRDCXALFUcUMqh4MleOwFyDyDVinsc82csXvEpCxWW4JshMiu9AkACJf3zPkVVbvXV/w8afUb3oRuuqWtrrfA/fZYrahPP72vi47izmxVM06mBP2mJDJqT5AXe96s/QODjkoJnle8AMIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALeEFg/tdXxjINT0gct67Xk8V1YzqxB9qehS48NOQOHSBk8CpzXe6TVAdBO7N8Qtpx9yMTgaZWf1uL0g/YL7UqvQvQQGurUDByH6hJMYqH0FehS/DY++4HBjNzLB+UYE4u07RWF1BtFzGv6O2cT+IJK7b0RMdkdyq/btb4pIr1SlAgMBAAECgYBSjWeDSLWF++2j/k145FZ5KAy9qUZ1h6Hr8YWlsMj9DIZr+myPjjPoEZNl4N0oMcUxHyRG8ZDWYDH3WiTQTBAWmM/qxUusUnpT9VAz9soU6h6UIABL3sAomC/EIiILIdX1/NE4rU6ZZAWKK/zBOcDibLTUQYGFSBwImWsJieKtTQJBAOazG1tVWTdodN7LS49WmEYgbPKngfAd2ersM86V1W/vf/MbzMU8dUDAUuDT5CX8Hbt2CFN9iM6SOEuL46F4uAMCQQDLpEwnu2GYPgQGcF6v1MHhmAk5Edm/0vq1vBRAnBs7cKDCi/PamReeA3f5KZVPb6nuA4ZNB8uOgrAgfXIk9UQ3AkAQBENT9fr5s9DZXvfh/bsYs0udFTAaQZhNjRSTtSdaXCBf+oAe+XGSi0e1wEBIutY9m5Y8ZazH2rL52750CXONAkA/OICUDS5dT1N8lEIxIEIYwVSLrSkGzn8bEIrRDCXALFUcUMqh4MleOwFyDyDVinsc82csXvEpCxWW4JshMiu9AkACJf3zPkVVbvXV/w8afUb3oRuuqWtrrfA/fZYrahPP72vi47izmxVM06mBP2mJDJqT5AXe96s/QODjkoJnle8A";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3hBYP7XV8YyDU9IHLeu15PFdWM6sQfanoUuPDTkDh0gZPAqc13uk1QHQTuzfELacfcjE4GmVn9bi9IP2C+1Kr0L0EBrq1Awch+oSTGKh9BXoUvw2PvuBwYzcywflGBOLtO0VhdQbRcxr+jtnE/iCSu29ETHZHcqv27W+KSK9UpQIDAQAB";
        RSA rsa = new RSA(privateKey, publicKey);
        
    }
}
