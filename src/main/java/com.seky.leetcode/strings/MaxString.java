package com.seky.leetcode.strings;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wf
 * @create: 2021/6/15 10:57
 * @description:
 */
public class MaxString {
    
    /**
     * 暴力解法:
     * (1)两个指针head 和 tail指向字符型头
     * (2)temp记录head和tail指针区间子字符串,tail每向后移动一步时,判断temp是否包含tail
     * (3)当temp包含tail时,再将head移动到下一个串,tail再从head的位置向后移动.
     * (4)在移动过程中需要记录一下temp的最长长度即可.
     * <p>
     * 时间复杂度: n^2
     * 空间复杂度: 2n
     *
     * @param source
     * @return
     */
    public Integer getMaxSubString(String source) {
        String result = "";
        for (int head = 0; head < source.length(); head++) {
            String temp = "";
            for (int tail = head; tail < source.length(); tail++) {
                String t = source.substring(tail, tail + 1);
                if (temp.contains(t)) {
                    break;
                } else {
                    temp = source.substring(head, tail + 1);
                }
                result = temp.length() > result.length() ? temp : result;
            }
        }
        System.out.println(result);
        return result.length();
    }
    
    /**
     * head 和 tail两个指针形成一个滑动窗口: head是区间窗口左边界, tail是滑动窗口右边界
     * (1)当tail向右移动时,进入滑动窗口的字符需要不重复这个要求; 当不满足要求时,只需把字符从移动滑动左边移除即可(移动head指针)
     * (2)移动滑动窗口时,需要记录窗口内的字符串大小.
     * 
     * 时间复杂度: n
     * 空间复杂度: n
     * 
     * @param source
     * @return
     */
    public int jumpMove(String source) {
        char[] chars = source.toCharArray();
        int head = 0;
        int maxL = 0;
        Map<Character, Integer> map = new HashMap<>(chars.length);
        for (int tail = 0; tail < chars.length; tail++) {
            Integer index = map.get(chars[tail]);
            if (index != null) {
                head = index + 1;
            }
            //index先移动后求maxL
            maxL = (tail - head +1) > maxL ? (tail - head +1) : maxL;
            map.put(chars[tail], tail);
        }
        return maxL;
    }
    
    @Test
    public void testMain() {
        String source = "12333";
        System.out.println(getMaxSubString(source));
        System.out.println(jumpMove(source));
    }
}
