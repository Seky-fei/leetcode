package com.seky.leetcode.BFS.齐头并进遍历;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author: wf
 * @create: 2021/10/11 16:43
 * @description:
 */
public class 无向图中的连通分量数 {
    
    @Test
    public void testMain(){
        int[][] edges = new int[][]{{0, 1}, {1, 3}, {1, 2}};
        int n = 4;
        
        System.out.println(countComponents(n, edges));
    }
    
    
    public int countComponents(int n, int[][] edges){
        //构建图：一个节点有N-1条无向边
        List<Integer>[] adj = new ArrayList[n];
        for(int i = 0; i < n; i++){
            adj[i] = new ArrayList<>();    
        }
        
        //无向图数据初始化：无向边列表数组处理(无向图，所以需要添加双向引用)
        for(int[] edge : edges){
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        
        //广度优先遍历
        int rt = 0;
        boolean[] visited = new boolean[n];
        for(int i = 0; i < n; i++){
            if(!visited[i]){
                //广度优先搜索
                //bfs(i, visited, adj);
                dfs2(i, visited, adj);
                rt ++;
            }
        }
        return rt;
    }
    
    /**
     * @param node
     * @param visited
     * @param adj
     */
    public void bfs(int node, boolean[] visited, List<Integer>[] adj){
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(node);
        visited[node] = true;
        while (!queue.isEmpty()){
            //记录同一层节点个数,并加入队列中
            int currentSize = queue.size();
            for(int i = 0; i < currentSize; i++){
                //取出队列首元素
                Integer fond = queue.poll();
                //队列首元素的后继节点, 
                List<Integer> fondList = adj[fond];
                for (int tag : fondList){
                    if(!visited[tag]){
                        queue.offer(tag);
                        visited[tag] = true;
                    }
                }
            }
        }
    }
    
    public void dfs2(int node, boolean[] visited, List<Integer>[] adj){
    /*    Queue<Integer> queue = new LinkedList<>();
        queue.offer(node);
        visited[node] = true;
        while(!queue.isEmpty()){
            Integer fond = queue.poll();
            //获得队首结点的所有后继结点
            List<Integer> fondList = adj[fond];
            //遍历后继节点，如果节点没有被访问过，就加入队列，同时标记节点已经访问过
            for(int tag : fondList){
                if(!visited[tag]){
                    queue.offer(tag);
                    //特别注意：在加入队列以后一定要将该结点标记为访问，否则会出现结果重复入队的情况
                    visited[tag] = true;
                }
            }
        }*/
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(node);
        visited[node] = true;
    
        while (!queue.isEmpty()) {
            Integer front = queue.poll();
            // 获得队首结点的所有后继结点
            List<Integer> successors = adj[front];
            for (int successor : successors) {
                if (!visited[successor]) {
                    queue.offer(successor);
                    // 特别注意：在加入队列以后一定要将该结点标记为访问，否则会出现结果重复入队的情况
                    visited[successor] = true;
                }
            }
        }
    }
    
    
    public static void main(String[] args) {
        int[][] temp = new int[2][2];
        
        temp[0][0] = 0;
        temp[0][1] = 1;
    
        temp[1][0] = 10;
        temp[1][1] = 11;
        
        System.out.println(temp);
        for(int[] t : temp){
            System.out.println(Arrays.toString(t));
        }
    }
    
}
