package com.seky.leetcode.dynamic;

import org.junit.jupiter.api.Test;

/**
 * @author: wf
 * @create: 2021/8/10 15:03
 * @description:
 * 
 * 给定一个数组，求数组的最大不连续递增子序列的长度
 * 如：arr[] = {3,1,4,1,5,9,2,6,5}的最长递增子序列长度为4。
 * 
 */
public class 数组最大不连续递增子序列 {
    @Test
    public void test(){
        int[] numbers = new int[]{3, 1, 4, 1, 5, 9, 2, 6, 5};
        for (int i = 0; i < numbers.length; i++){
            System.out.println(findMaxSkipSub1(numbers, i));
            System.out.println(findMaxSkipSub2(numbers, i));
            System.out.println("====================================");
        }
    }
    
    /**
     * 问题拆解 ---> 确认状态 ---> 状态转移方程
     *  状态转移方程式: 数组第n个元素处的最大不连续递增子序列长度 f(n)
     *      若 a[n+1] > a[n]时，f(n) = f(n+1) +1; 其他情况 f(n) = f(n+1)
     * 
     * @param numbers
     * @param n
     * @return
     */
    public int findMaxSkipSub1(int[] numbers, int n){
        //临界条件
        if(n >= numbers.length -1){
            return 0;
        }
        //状态转移方程
        return numbers[n+1] > numbers[n] ?  findMaxSkipSub1(numbers, n+1) + 1 : findMaxSkipSub1(numbers, n+1);
    }
    
    /**
     * 方法一：没有保存子问题的解，会导致子问题会被重复计算，增加了复杂度。
     *  
     *  比如：再求f(1) 和 f(2)时，f(3)都会被计算一次，导致重复计算
     *  
     *  
     * @param numbers
     * @param n
     * @return
     */
    private int[] temp = new int[10];
    public int findMaxSkipSub2(int[] numbers, int n){
        if(n >= numbers.length-1){
            return 0;
        }
        //将每个子问题的解保存下来
        if(temp[n] == 0){
            int count = numbers[n+1] > numbers[n] ?  findMaxSkipSub2(numbers, n+1) + 1 : findMaxSkipSub2(numbers, n+1);
            temp[n] = count;
        }
        return temp[n];
    }
}
