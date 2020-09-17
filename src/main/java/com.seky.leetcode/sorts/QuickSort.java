package com.seky.leetcode.sorts;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author: feiwang_6
 * @create: 2020/9/16 22:24
 * @description:
 */
public class QuickSort {

    @Test
    public void test(){
        int[] tag = new int[]{9, 10, 3, 1, 8, 2, 6, 7, 2, 5, 4, 0};
        QuickSort(tag);
        System.out.println(Arrays.stream(tag).boxed().collect(Collectors.toList()));
    }
    
    /**
     * 快排(时间复杂度:N*lgN, 空间复杂度: lgN)
     * @param numbers
     */
    public void QuickSort(int[] numbers){
        //SortIterationLeft(numbers, 0, numbers.length-1);
        SortIterationRight(numbers, 0, numbers.length-1);
    }

    /**
     * 快排注意点：1.当基数取左边时，每次都是先移动右边指针。(防止两个指针相遇交换基准数时，把大于基数的元素交换到基数的左边)
     * @param numbers
     * @param start
     * @param end
     */
    public void SortIterationLeft(int[] numbers, int start, int end){
        //递归退出条件
        if(start >= end){
            return;
        }
        int h = start, t = end;
        //参考基数tag
        int tag = numbers[h];
        while (h != t){
            //移动右边的t指针，找到小于temp的数
            if(numbers[t] >= tag){
                t--;
                continue;
            }
            //移动头左边的h指针，找到大于temp的数
            if(numbers[h] <= tag){
                h++;
                continue;
            }
            //交换元素
            if(numbers[t] < tag && numbers[h] > tag){
                swapEnum(numbers, h, t);
            }
        }
        //相遇时，交换基准数
        swapEnum(numbers, start, h);
        //左边
        SortIterationLeft(numbers, start, h - 1);
        //右边
        SortIterationLeft(numbers, h + 1, end);
    }

    /**
     * 元素交换
     * @param numbers
     * @param index1
     * @param index2
     */
    public void swapEnum(int[] numbers, int index1, int index2){
        int temp = numbers[index1];
        numbers[index1] = numbers[index2];
        numbers[index2] = temp;
    }

    /**
     * 基数取右边时，每次都先移动左边指针(防止两个指针相遇交换基准数时，把小于基数的元素交换到基数的右边)
     * @param numbers
     * @param start
     * @param end
     */
    public void SortIterationRight(int[] numbers, int start, int end){
        //递归结束条件
        if(start >= end){
            return;
        }
        int h = start, t = end;
        //参考基数
        int tag = numbers[end];
        while (h != t){
            //参考基数取右边时，先移动左边指针
            if(numbers[h] <= tag){
                h++;
                continue;
            }
            //再移动右边
            if(numbers[t] >= tag){
                t--;
                continue;
            }
            //左边大于基数，右边小于基数时，互换
            if(numbers[h] > tag && numbers[t] < tag){
                swapEnum(numbers, h, t);
            }
        }
        //交换参考基数和相遇位置元素
        swapEnum(numbers, h, end);
        //左边
        SortIterationRight(numbers, start, h -1);
        //右边
        SortIterationRight(numbers, h + 1, end);
    }
}
