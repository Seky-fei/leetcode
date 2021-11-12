package com.seky.leetcode.BFS.双向和多源BFS;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author: wf
 * @create: 2021/11/4 19:16
 * @description:
 */
public class 单词接龙问题 {
    
    @Test
    public void testMath() {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"));
        System.out.println(ladderLength(beginWord, endWord, wordList));
        
        String beginWord2 = "hot";
        String endWord2 = "dog";
        List<String> wordList2 = new ArrayList<>(Arrays.asList("hot", "dog"));
        System.out.println(ladderLength(beginWord2, endWord2, wordList2));
    }
    
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //1.构建无向图结构：beginWord不在时，放入到字典中
        if (!wordList.contains(beginWord)) {
            wordList.add(beginWord);
        }
        Map<String, Set<String>> map = new HashMap<>();
        //1.构建无向图: beginWord不在时，放入到字典中
        for (int i = 0; i < wordList.size(); i++) {
            for (int j = i + 1; j < wordList.size(); j++) {
                String one = wordList.get(i);
                String two = wordList.get(j);
                boolean checkChange = checkChange(one, two);
                if (checkChange) {
                    Set<String> oneSet = map.getOrDefault(one, new HashSet<>());
                    oneSet.add(two);
                    map.put(one, oneSet);
    
                    Set<String> twoSet = map.getOrDefault(two, new HashSet<>());
                    twoSet.add(one);
                    map.put(two, twoSet);
                }
            }
        }
        
        int result = 0;
        boolean sign = false;
        //BFS遍历
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(beginWord);
        visited.add(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            result++;
            for (int i = 0; i < size; i++) {
                String curNode = queue.poll();
                if(curNode.equals(endWord)){
                    return result;
                }
                Set<String> nexts = map.get(curNode);
                if(nexts != null){
                    for (String next : nexts) {
                        if (!visited.contains(next)) {
                            queue.offer(next);
                            visited.add(next);
                        }
                    }
                }
            }
        }
        return 0;
    }
    
    /**
     * 检查两个是否能转换
     *
     * @param word1
     * @param word2
     * @return
     */
    public boolean checkChange(String word1, String word2) {
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        int tag = 0;
        for (int i = 0; i < chars1.length; i++) {
            if (chars1[i] != chars2[i]) {
                tag++;
            }
        }
        return tag == 1 ? true : false;
    }
    
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> integers = list.subList(0, 2);
        integers.size();
    }
}
