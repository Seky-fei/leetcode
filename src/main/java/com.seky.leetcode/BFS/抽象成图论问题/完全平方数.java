package com.seky.leetcode.BFS.抽象成图论问题;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: wf
 * @create: 2021/10/25 15:40
 * @description: 问题抽象成图论问题
 */
public class 完全平方数 {
    
    @Test
    public void testMath() {
        System.out.println(findMinPath(12));
        
        System.out.println(findMinPath(13));
    }
    
    /**
     * 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
     *
     * @param n
     * @return
     */
    public int findMinPath(int n) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n+1];
        queue.offer(n);
        visited[n] = true;
        //记录扫描的层次
        int depth = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) {
                Integer val = queue.poll();
                //每层中的每个节点都需要寻找下一层节点
                for (int j = 1; j * j <= val; j++) {
                    int nextVal = val - j * j;
                    if(nextVal == 0){
                        return depth;
                    }
                    if(nextVal > 0 && !visited[nextVal]){
                        queue.offer(nextVal);
                        visited[nextVal] = true;
                    }
                }
            }
        }
        return depth;
    }
}
