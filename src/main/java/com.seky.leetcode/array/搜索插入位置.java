package com.seky.leetcode.array;

import org.junit.jupiter.api.Test;

/**
 * @author: wf
 * @create: 2021/8/6 10:33
 * @description:
 */
public class 搜索插入位置 {
    
    @Test
    public void test(){
        int[] numbers = new int[]{-3, -2, 0, 1, 3, 5, 6, 7, 10};
        int target = 200;
        System.out.println(searchElement1(numbers, target));
        System.out.println(searchElement2(numbers, target, 0, numbers.length-1));
        System.out.println(optimizeSearchElement(numbers, target));
    }
    
    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 
     * @param numbers
     * @param target
     * @return
     */
    public int searchElement1(int[] numbers, int target){
        int head = 0;
        int index = 0;
        int tail = numbers.length - 1;
        while (head < tail){
            index = (head + tail)/2;
            if(numbers[index] == target){
                return index;
            }
            if(target > numbers[index]){
                head = index + 1;
            }else if(target < numbers[index]){
                tail = index - 1;
            }
        }
        //不存在
        return target > numbers[head] ? head + 1 : head;
    }
    
    /**
     * 优化: 注意理解题意, 并抽象成看得懂的数学模型
     *      1.target不存在数组中, 假设插入位置是pos, 则 numbers[post-1] < target < numbers[pos]
     *      2.target存在数组中, 假设插入位置是pos, 则 target = numbers[pos]
     *      
     *      ====> 将两个条件合并得出最后的目标：在一个有序数组中找第一个大于等于 target 元素的下标。
     * 
     * @param numbers
     * @param target
     * @return
     */
    public int optimizeSearchElement(int[] numbers, int target){
        int left = 0;
        int result = numbers.length;
        int right = result - 1;
        while (left <= right){
            int index = (right + left) >> 1;
            if(numbers[index] >= target){
                result = index;
                right = index-1;
            }else {
                left = index +1;
            }
        }
        return result;
    }
    
    /**
     * 动态规划
     * @param numbers
     * @param target
     * @param begin
     * @param end
     * @return
     */
    public int searchElement2(int[] numbers, int target, int begin, int end){
        //递归结束
        if(begin >= end){
            return target > numbers[begin] ? begin + 1 : begin;
        }
        int index = (begin + end)/2;
        if(numbers[index] == target){
            return index;
        }
        if(target > numbers[index]){
            begin = index + 1;
        }else if(target < numbers[index]){
            end = index - 1;
        }
        return searchElement2(numbers, target, begin, end);
    }
    
    public static void main(String[] args) {
        Double.valueOf('c');
    }
}
