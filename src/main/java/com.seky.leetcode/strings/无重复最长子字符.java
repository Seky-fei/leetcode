package com.seky.leetcode.strings;

import org.junit.jupiter.api.Test;

/**
 * @author: wangfei
 * @create: 2020/9/14 11:14
 * @description: 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class 无重复最长子字符 {

    @Test
    public void test(){
        String str = "abcabcbb";
        System.out.println(findMaxSubStr(str));
        System.out.println(lengthOfLongestSubstring(str));
    }
    
    /**
     * (错误方法: 每次更新的时候都保存tail到array中)
     * 移动窗口保存不重复的相邻字符
     * @param str
     * @return
     */
    public int findMaxSubStr(String str){
        if(str == null || str.length() == 0){
            return 0;
        }
        //滑动窗口头和尾
        int h = 0, t = 0;
        int count = 0;
        int[] array = new int[200];
        while (t < str.length()){
            char c = str.charAt(t);
            //存在时,修改滑动窗口头(这种修改会存在问题,当重复字符是第一个时:例如abcabcbb, t=3时array[c]=0,就不会进入滑动窗口修改)
            if(array[c] > h){
                h = array[c] + 1;
            }
            count = Math.max(count, t - h + 1);
            //修改滑动窗口t和记录窗口内的值
            array[c] = t++;
        }
        return count;
    }

    /**
     * (正确方法: 每次更新的时候都保存tail+1到array中)
     * 1.滑动窗口head-tail,
     * 2.array[]来记录一个字母如果后面出现重复时，i应该调整到的新位置, 所以每次更新的时候都会保存tail+1 ，即字母后面的位置
     * 3.tail表示子串的最后一个字母，计算子串长度为 tail-head + 1
     *
     * @param str
     * @return
     */
    public int lengthOfLongestSubstring(String str) {
        int[] array = new int[128];
        int count = 0, head = 0, tail = 0;
        while (tail < str.length()){
            char c = str.charAt(tail);
            //存在时,修改滑动窗口头
            head = head >= array[c] ? head : array[c];
            //计算滑动窗口内计数字符大小
            count = count >= tail-head+1 ? count : tail-head+1;
            //每次更新的时候都会保存tail+1，即字母后面的位置(避免array默认值为0的影响)
            array[c] = ++tail;
        }
        return count;
    }
}
