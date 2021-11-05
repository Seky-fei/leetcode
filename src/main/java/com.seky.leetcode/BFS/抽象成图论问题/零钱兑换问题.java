package com.seky.leetcode.BFS.抽象成图论问题;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: wf
 * @create: 2021/11/2 11:18
 * @description:  此题跟 完全平方数同一个解法
 */
public class 零钱兑换问题 {
    
    @Test
    public void testMath(){
        int[] coins = new int[]{1, 2, 5};
        System.out.println(coinChange(coins, 100));
    }
    
    public int coinChange(int[] coins, int amount){
        boolean[] visited = new boolean[amount+1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(amount);
        visited[amount] = true;
        
        int result = 0;
        while (!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                int sum = queue.poll();
                if(sum == 0){
                    return result;
                }
                for(int coin : coins){
                    int nextVal = sum - coin;
                    if(nextVal >= 0 && !visited[nextVal]){
                        queue.offer(nextVal);
                        visited[nextVal] = true;
                    }
                }
            }
            result++;
        }
        return -1;
    }
}
