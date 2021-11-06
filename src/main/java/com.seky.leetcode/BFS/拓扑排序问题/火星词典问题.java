package com.seky.leetcode.BFS.拓扑排序问题;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author: wf
 * @create: 2021/11/4 11:01
 * @description: 「力扣」第 269 题：火星词典（困难）
 */
public class 火星词典问题 {
    @Test
    public void testMath() {
        //String[] words = new String[]{"wrt", "wrf", "er", "ett", "rftt"};
        String[] words = new String[]{"abc", "ab"};
        
        System.out.println(alienOrder(words));
        
    }
    
    
    public String alienOrder(String[] words) {
        //1.构建邻接表的数据结构
        Map<Character, Set<Character>> map = new HashMap<>();  //注意：["a"], ["abc", "abc"]是不会进入map有向图结构中
        for (int i = 0; i < words.length - 1; i++) {
            //将相邻字符进行两两对比，只有第一个不相同的单词可以确定其先后顺序关系
            int preLen = words[i].length();
            int postLen = words[i + 1].length();
            for (int j = 0; j < preLen && j < postLen; j++) {
                char preChar = words[i].charAt(j);
                char beChar = words[i + 1].charAt(j);
                //找到相邻两个字符的第一个不同字母（注意：只有第一个不同字母能确定其向后顺序）
                if (preChar != beChar) {
                    Set<Character> set = map.getOrDefault(preChar, new HashSet<>());
                    set.add(beChar);
                    map.put(preChar, set);
                    //只找第一个不同的字母
                    break;
                }
    
                // 如输入["abc","ab"]时，无法判断字母顺序，返回空
                if (preLen > postLen && j == postLen - 1) {
                    return "";
                }
            }
        }
        
        //2.构建入度数据结构
        int[] inDegree = new int[26];  //只有26个字母
        //将入度的初始值赋为-1(方便确定哪些字母没在字符数组中)
        Arrays.fill(inDegree, -1);
        //初始化入度数据：注意，不是26字母都在words中出现，所以入度分为两种情况：没有出现的字母入度为-1，出现了的字母的入度为非-1(0或正数)
        for (String str : words) {
            //将出现过的字符的出度设定为0： 为什么要把出现了的字母的入度赋值为0？？？？？
            for (char c : str.toCharArray())
                inDegree[c - 'a'] = 0;
        }
        for (char key : map.keySet()) {
            for (char val : map.get(key)) {
                inDegree[val - 'a']++;
            }
        }
        //入度数据初始化结束后，存在三种情况：没有出现的字母入度为-1，字母是边缘节点入度为0(比如words只有一个字母时)，字母为中间节点入度>0
        
        
        //3.寻找顶点的前驱结点(入度为0的节点，作为BFS遍历的起点)
        Queue<Character> queue = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if(inDegree[i] != -1)
                count++;
            if (inDegree[i] == 0) {
                char c = (char) (i + 'a');
                queue.offer(c);
            }
        }
        
        //BFS遍历
        StringBuilder builder = new StringBuilder();
        while (!queue.isEmpty()) {
            char node = queue.poll();
            builder.append(node);
            Set<Character> nextSet = map.get(node);
            if (nextSet == null) {
                continue;
            }
            for (Character c : nextSet) {
                inDegree[c - 'a']--;
                if (inDegree[c - 'a'] == 0) {
                    queue.offer(c);
                }
            }
        }
        
        //4.有向图环判断
        String string = builder.toString();
        if(string.length() != count){
            return "";
        }
        return string;
    }
}
