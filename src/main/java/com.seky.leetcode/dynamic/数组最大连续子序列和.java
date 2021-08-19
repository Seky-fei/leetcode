package com.seky.leetcode.dynamic;

import org.junit.jupiter.api.Test;

/**
 * @author: wf
 * @create: 2021/8/10 19:37
 * @description: 给出一个数组numbers[], 求数组中最大连续子序列和
 */
public class 数组最大连续子序列和 {
    
    @Test
    public void test() {
        int[] numbers = new int[]{6, -1, 3, -4, -6, 9, 2, -2, 5};
        //int[] numbers = new int[]{5, -1, 1};
        for (int i = 0; i < numbers.length - 1; i++) {
            System.out.println(getSubMaxCount(numbers, i));
            System.out.println(getSubMaxCountOpt(numbers, i));
            System.out.println("=====================================");
        }
        System.out.println("111111111111111111111111111111111111");
        System.out.println(getSubMaxCountOpt2(numbers));
    }
    
    /**
     * 1.问题拆解：用f(n)表示index=n处的最大连续子序列和，所有f(n)可以看成是 a(n)元素 和 后面的f(n+1)组成的 
     * 2.确定状态：三个状态 f(n), a(n) 和 f(n+1)
     * 3.状态转移方程式: f(n) = Max{f(n+1)+a(n), a(n)}  状态方程式容易写成：f(n) = Max{f(n+1)+a(n), f(n+1)}
     * 
     * @param numbers
     * @param n
     * @return
     */
    public int getSubMaxCount(int[] numbers, int n) {
        if (n >= numbers.length - 1) {
            return numbers[n];
        }
        //状态转移方程
        return Math.max(numbers[n], (getSubMaxCount(numbers, n + 1) + numbers[n]));
    }
    
    /**
     * 优化：保存子问题的解，避免重复求解子问题
     * @param numbers
     * @param n
     * @return
     */
    int[] temp = new int[10]; //numbers.length长度就可以
    public int getSubMaxCountOpt(int[] numbers, int n) {
        if (n >= numbers.length - 1) {
            return numbers[n];
        }
        //保存子问题的解
        if(temp[n] == 0){
            //状态转移方程式
            int count = Math.max(getSubMaxCountOpt(numbers, n+1) + numbers[n], numbers[n]);
            temp[n] = count;
        }
        return temp[n];
    }
    
    /**
     * 不使用递归解决动态规划: f(n) = Max[f(n-1)+a(n), a(n)] 最大和子序列中最后一个index=n, f(n)可以看成是a(n) 和 前面的f(n-1)组成
     * @param numbers
     * @return
     */
    public int getSubMaxCountOpt2(int[] numbers){
        int pre = 0; 
        int sumMax = 0;
        for(int n : numbers){
            pre = Math.max(pre + n, n);
            sumMax = Math.max(pre, sumMax);
        }
        return sumMax;
    }
    
    public static void main(String[] args) {
        System.out.println(Math.round(2.9));
    }
}
