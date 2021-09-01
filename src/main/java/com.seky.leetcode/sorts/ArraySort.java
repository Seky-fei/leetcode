package com.seky.leetcode.sorts;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author: wf
 * @create: 2021/9/1 17:29
 * @description: 数组排序
 */
public class ArraySort {
    
    @Test
    public void testSort(){
        Integer[] numbers = new Integer[]{-9, -10, 1, 8, 0, 4};
        //bubble_sort(numbers);
        //select_sort(numbers);
        inserting_sort(numbers);
        System.out.println("11111111111111");
        System.out.println(Arrays.asList(numbers));
    }
    
    /**
     * 冒泡排序
     * @param numbers
     */
    public void bubble_sort(Integer[] numbers){
        for(int i = 0; i < numbers.length; i++){
            for(int j = 0; j < numbers.length - i - 1; j++){
                if(numbers[j] > numbers[j+1]){
                    int temp = numbers[j];
                    numbers[j] = numbers[j+1];
                    numbers[j+1] = temp;
                }
            }
        }
    }
    
    /**
     * 选择排序
     * 算法思想:从头至尾扫描序列，找出最小的一个元素，和第一个元素交换，接着从剩下的元素中继续这种选择和交换方式，最终得到一个有序序列。
     * @param numbers
     */
    public void select_sort(Integer[] numbers){
        for(int i = 0; i < numbers.length; i++){
            int min = i;
            for(int j = i+1; j < numbers.length; j++){
                if(numbers[j] < numbers[i]){
                    min = j;
                }
            }
            int temp = numbers[i];
            numbers[i] = numbers[min];
            numbers[min] = temp;
        }
    }
    
    /**
     * 插入排序
     * 基本思想：每一步将一个待排序的数据插入到前面已经排好序的有序序列中，直到插完所有元素为止。
     *  
     * @param numbers
     */
    public void inserting_sort(Integer[] numbers){
        for(int i = 1; i < numbers.length; i++){
            int temp = numbers[i];
            for(int j = i - 1; j >= 0; j--){
                if(numbers[j] > temp){
                    numbers[j+1] = numbers[j];
                    //空出来的位置，把temp放进去
                    numbers[j] = temp;
                }
            }
        }
    }
}
