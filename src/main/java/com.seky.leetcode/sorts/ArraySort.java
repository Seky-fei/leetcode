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
    public void testSort() {
        Integer[] numbers = new Integer[]{-9, -10, 1, 8, 0, 4, -1};
        //bubble_sort(numbers);
        //select_sort(numbers);
        //inserting_sort1(numbers);
        //inserting_sort2(numbers);
        //quick_sort(numbers, 0, numbers.length - 1);
        //numbers = merge_sort(numbers, 0, numbers.length - 1);
        merge_sort2(numbers, 0, numbers.length - 1);
        System.out.println("11111111111111");
        System.out.println(Arrays.asList(numbers));
    }
    
    /**
     * 冒泡排序
     *
     * @param numbers
     */
    public void bubble_sort(Integer[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length - i - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
    }
    
    /**
     * 选择排序
     * 算法思想:从头至尾扫描序列，找出最小的一个元素，和第一个元素交换，接着从剩下的元素中继续这种选择和交换方式，最终得到一个有序序列。
     *
     * @param numbers
     */
    public void select_sort(Integer[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int min = i;
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[j] < numbers[i]) {
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
    public void inserting_sort1(Integer[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            int temp = numbers[i];
            for (int j = i - 1; j >= 0; j--) {
                if (numbers[j] > temp) {
                    numbers[j + 1] = numbers[j];
                    //空出来的位置，把temp放进去
                    numbers[j] = temp;
                }
            }
        }
    }
    
    public void inserting_sort2(Integer[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            int temp = numbers[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                //有序部分元素向后移
                if (numbers[j] > temp) {
                    numbers[j + 1] = numbers[j];
                } else {
                    break;
                }
            }
            //空出来的位置，把temp放进去
            numbers[j + 1] = temp;
        }
    }
    
    /**
     * 快速排序
     *
     * @param numbers
     * @param start
     * @param end
     */
    public void quick_sort(Integer[] numbers, int start, int end) {
        if (start >= end) {
            return;
        }
        int h = start, t = end;
        int temp = numbers[h];
        while (h != t) {
            if (numbers[t] >= temp) {
                t--;
                continue;
            }
            if (numbers[h] <= temp) {
                h++;
                continue;
            }
            //右边元素小于temp + 左边元素大于temp 交换元素
            swapItem(numbers, h, t);
        }
        //指针相遇：相与处元素与基准数据交换
        swapItem(numbers, start, h);
        //递归
        quick_sort(numbers, start, h - 1);
        quick_sort(numbers, h + 1, end);
    }
    
    public void swapItem(Integer[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }
    
    
    /**
     * 归并排序（分：二分法拆解 ----> 治：合并两个有序数组）
     *
     * @param numbers
     * @param start
     * @param end
     * @return
     */
    public Integer[] merge_sort(Integer[] numbers, int start, int end) {
        if (start == end) {
            int temp = numbers[start];
            return new Integer[]{temp};
        }
        //分：将数组二分拆成两个数组
        int mid = (start + end) / 2;
        Integer[] tag1 = merge_sort(numbers, start, mid);
        Integer[] tag2 = merge_sort(numbers, mid + 1, end);
        
        //治：两个有序数组合并
        Integer[] merger = merger(tag1, tag2);
        return merger;
    }
    
    
    public void merge_sort2(Integer[] numbers, int start, int end) {
        if (start >= end) {
            return;
        }
        //分：将数组二分拆成两个数组
        int mid = (start + end) / 2;
        merge_sort2(numbers, start, mid);
        merge_sort2(numbers, mid + 1, end);
        
        //治：合并两个有序数组
        mergeItem(numbers, start, mid, mid + 1, end);
    }
    
    /**
     * 直接在源数组上merge两个有序数组：在source数组上，用下标表示两个有序数组
     *
     * @param numbers
     * @param st1
     * @param end1
     * @param st2
     * @param end2
     */
    public void mergeItem(Integer[] numbers, int st1, int end1, int st2, int end2) {
        int length1 = end1 - st1 + 1;
        int length2 = end2 - st2 + 1;
        int len = length1 + length2;
        Integer[] temp = new Integer[len];
        int i = st1, j = st2;
        int index = 0;
        while (index < len) {
            //两个数组其中一个超出
            if (i > end1) {
                temp[index++] = numbers[j++];
                continue;
            }
            if (j > end2) {
                temp[index++] = numbers[i++];
                continue;
            }
            
            //两个数组都没超出
            temp[index++] = (numbers[i] <= numbers[j]) ? numbers[i++] : numbers[j++];
        }
        //合并后temp ---> 移动到源数组numbers
        for (int k = 0; k < temp.length; k++) {
            numbers[st1 + k] = temp[k];
        }
    }
    
    /**
     * 将两个有序数组合并成一个有序数组: 数组操作
     *
     * @param tag1
     * @param tag2
     * @return
     */
    public Integer[] merger(Integer[] tag1, Integer[] tag2) {
        int len = tag1.length + tag2.length;
        Integer[] temp = new Integer[len];
        int i = 0, j = 0;
        int index = 0;
        while (index < len) {
            //超出
            if (i >= tag1.length) {
                temp[index++] = tag2[j++];
                continue;
            }
            if (j >= tag2.length) {
                temp[index++] = tag1[i++];
                continue;
            }
            //都没超出
            if (tag1[i] <= tag2[j]) {
                temp[index++] = tag1[i++];
            } else {
                temp[index++] = tag2[j++];
            }
        }
        return temp;
    }
    
    @Test
    public void testMerge() {
        Integer[] tag1 = new Integer[]{-1, 1, 3, 5, 9, 9};
        Integer[] tag2 = new Integer[]{8, 9, 50};
        Integer[] merger = merger(tag1, tag2);
        System.out.println(Arrays.asList(merger));
    }
}
