package com.seky.leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author: wf
 * @create: 2021/7/27 13:57
 * @description:
 */
public class 删除有序数组中的重复项 {
    
    /**
     * 给你一个有序数组 nums ，请原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     */
    @Test
    public void testMethod(){
        int[] numbers = new int[]{1, 2, 3, 3, 4, 4, 5, 5, 8};
        //int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(removeDuplicate1(numbers));
        System.out.println(Arrays.toString(numbers));
        System.out.println();
        numbers = new int[]{1, 2, 3, 3, 4, 4, 5, 5, 8};
        System.out.println(removeDuplicate2(numbers));
        System.out.println(Arrays.toString(numbers));    
        System.out.println();
        numbers = new int[]{1, 2, 3, 3, 4, 4, 5, 5, 8};
        System.out.println(removeDuplicate3(numbers));
        System.out.println(Arrays.toString(numbers));
    }
    
    /**
     * 暴力解法  遍历numbers数组, 当numbers[i] >= numbers[i+1]时, 就从i之后的元素里找到一个比numbers[i]大的替换掉i+1的元素
     *  时间复杂度O(n^2) 空间复杂度O(1)
     *  
     *  此方法会做一些无用功: 中间已经比较过的元素会重复比较
     *  
     * @param numbers
     * @return
     */
    public int removeDuplicate1(int[] numbers){
        int len = 0;
        int temp = 0;
        for(int i = 0; i < numbers.length; i++){
            len ++;
            if(i < numbers.length -1 && numbers[i] >= numbers[i+1]){
                for(int j = i+1; j < numbers.length; j++){
                    if(numbers[i] < numbers[j]){
                        temp = numbers[i+1];
                        numbers[i+1] = numbers[j];
                        numbers[j] = temp;
                        break;
                    }
                    //没有找到比numbers[i]大的元素: 结束
                    if(j == numbers.length -1){
                        return len;
                    }
                }
            }
        }
        return len;
    }
    
    /**
     * 优化算法(因为数组是有序的, 已经比较不需要重复比较)
     *   时间复杂度O(n) 空间复杂度O(1)
     *   
     *   一个在前记作 p，一个在后记作 q，算法流程如下：
     *   比较 p 和 q 的元素是否相等
     *   若相等, 将q向后一位
     *   若不相等, 将q的元素赋值个p+1, p后移以为,q也后移一位(有效的避开已经比较的元素再重复比较) 
     *   
     * @param numbers
     * @return
     */
    public int removeDuplicate2(int[] numbers){
        if(numbers.length <=1){
            return numbers.length;
        }
        int le = 0;
        int rt = 1;
        while(rt < numbers.length){
            if(numbers[le] != numbers[rt]){
                numbers[le+1] = numbers[rt];
                le++;
            }
            rt++;
        }
        return le+1;
    }
    
    /**
     * 方法二的变形: 两个指针, 使用index指向有效数组的最后一位, 一个指针i遍历一遍数组
     * @param numbers
     * @return
     */
    public int removeDuplicate3(int[] numbers){
        int index = 0;
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] != numbers[index]){
                numbers[++index] = numbers[i];
            }
        }
        return index+1;
    }
}
