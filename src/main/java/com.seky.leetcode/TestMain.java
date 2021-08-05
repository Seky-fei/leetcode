package com.seky.leetcode;

import org.junit.jupiter.api.Test;

/**
 * @author: wangfei
 * @create: 2020/9/14 16:35
 * @description:
 */
public class TestMain {
    
    @Test
    public void test(){
        int[] nums = new int[]{1,3,5,6};
        int target = 5;
        System.out.println(searchInsert1(nums, target));
        System.out.println(searchIndex2(nums, target, 0, nums.length));
        System.out.println("===================================");
        System.out.println(jumpStep(4));
    }

    /**
     * 二分查找(用循环)
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert1(int[] nums, int target){
        int head = 0;
        int tail = nums.length - 1;
        while (head < tail){
            int index = (head + tail)/2;
            if(nums[index] > target){
                tail = index;
            }
            if(nums[index] < target){
                head = index;
            }
            if(nums[index] == target){
                return index;
            }
        }
        return 0;
    }

    /**
     * 递归算法
     * @param numbers
     * @param target
     * @param begin
     * @param end
     * @return
     */
    public int searchIndex2(int[] numbers, int target, int begin, int end){
        int index = (begin + end)/2;
        if(numbers[index] == target){
            return index;
        }
        if(target > numbers[index]){
            begin = index;
        }else if(target < numbers[index]){
            end = index;
        }
        return searchIndex2(numbers, target, begin, end);
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
