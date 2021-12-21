package com.seky.leetcode.dynamic;

import org.junit.jupiter.api.Test;

/**
 * @author: wf
 * @create: 2021/12/10 11:14
 * @description:
 */
public class 跳台阶问题 {
    
    @Test
    public void testMain(){
        System.out.println(jumpStep(4));
    }
    
    /**
     * n个台阶, 每次调一个台阶或两个台阶, 有多少种跳法: 斐波那契数列(斐氏数列)  f(n) = f(n-1) + f(n-2)
     * @param n
     * @return
     */
    public int jumpStep(int n){
        if(n <= 2){
            return n;
        }
        int total = (jumpStep(n - 1) + jumpStep(n - 2));
        return total;
    }
}
