package com.seky.leetcode;

import java.util.LinkedList;

/**
 * @author: wangfei
 * @create: 2020/9/14 16:35
 * @description:
 */
public class TestMain {
    public static final String a = "123";
    public static final String b = "456";
    
    public static final String a1;
    public static final String b1;
    static {
        a1 = "123";
        b1 = "456";
    }
    
    public static void main(String[] args) {
        String string = "123456";
        
        String s1 = a + b;
        String s2 = a1 + b1;
        System.out.println("11111   " + (string == s1)); //true
        System.out.println("22222   " + (string == s2)); //false
        
        String w1 = "123";
        String w2 = "456";
        String w = w1 + w2;
        System.out.println(string == w); //false
        
        System.out.println("------------------------");
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addAll(null);
    }
}
