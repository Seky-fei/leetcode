package com.seky.leetcode;

import java.util.TreeMap;

/**
 * @author: wangfei
 * @create: 2020/9/14 16:35
 * @description:
 */
public class TestMain {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>((o1, o2) -> o1 < o2 ? -1 : 1);
        treeMap.put(1, 2);
        treeMap.put(2, 2);
        System.out.println(treeMap.get(1));
        System.out.println(treeMap.get(2));
    }
}
