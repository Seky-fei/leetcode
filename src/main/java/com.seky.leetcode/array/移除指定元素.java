package com.seky.leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author: wf
 * @create: 2021/7/27 18:46
 * @description:
 */
public class 移除指定元素 {
    /**
     *  给定一个数组numbers[] 和 一个数val, 请原地删除值等于val的元素，并返回删除后数组的新长度。 
     *  数组的元素顺序可以改变,不考虑numbers[]数组超出新长度的元素, 要求空间复杂度O(1)
     */
    
    @Test
    public void testMethod(){
        int[] numbers = new int[]{2, 2, 2, 2, 2, 2, 6};
        int val = 2;
        System.out.println(removeElement1(numbers, val));
        System.out.println(Arrays.toString(numbers));
        System.out.println("=============================");
        numbers = new int[]{1, 2, 2, 2, 2, 2, 1};
        val = 1;
        System.out.println(removeElement2(numbers, val));
        System.out.println(Arrays.toString(numbers));
    }
    
    /**
     * 解题思路: 跟删除有序数组重复元素一样
     * 
     * 两个指针index 和 i, 两个指针都从头向尾移动
     *  index指向numbers数组中有效元素的末尾+1的位置, 用i指针遍历数组numbers
     *      若 numbers[i] != val时,将i处的元素赋给index, index向右移一位
     *      
     *  时间复杂度 O(n), 空间复杂度 O(1)
     *  
     * @param numbers
     * @param val
     * @return
     */
    public int removeElement1(int[] numbers, int val){
        int index = 0;
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] != val){
                numbers[index++] = numbers[i];
            }
        }
        return index;
    }
    
    /**
     * 第一种方法是双指针都是从头开始, 会把整个数组遍历一遍(如[1, 3, 3, 4] val=4时,也会遍历一遍)
     *  优化方案: 两个指针head 和 tail 分别指向numbers数组的头尾, 循环两个指针向中间移动
     *      若numbers[head] = val, tail从尾向前找到一个不等于val的元素进行交换
     *  
     * @param numbers
     * @param val
     * @return
     */
    public int removeElement2(int[] numbers, int val){
        int tail = numbers.length - 1;
        int head = 0;
        while (head <= tail){
            if(numbers[head] == val){
                if(numbers[tail] != val){
                    numbers[head] = numbers[tail];
                    head++;
                }
                tail --;
            }else {
                head ++;
            }
        }
        return head;
    }
}
