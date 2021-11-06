package com.seky.leetcode.BFS.拓扑排序问题;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author: wf
 * @create: 2021/11/3 15:36
 * @description: 力扣 【310. 最小高度树】
 */
public class 最小高度树 {
    
    @Test
    public void testMath() {
        int[][] edges = {{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}};
        int n = 6;
        System.out.println(findMinHeightTrees(n, edges));
        System.out.println(findMinHeightTrees(1, new int[][]{}));
        System.out.println(findMinHeightTrees(2, new int[][]{{0, 1}}));
        System.out.println("==================================");
        System.out.println(findMinHeightTreesByOptimize(n, edges));
    }
    
    /**
     * 超时方法：根据题目的意思，对节点挨个bfs遍历，统计下每个节点的高度，然后用map存储起来，后面查询这个高度的集合里最小的就可以了
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (edges.length == 0) {
            return Arrays.asList(0);
        }
        //1.构建邻接表结构(无向图 --> 有向图)
        Set<Integer>[] adj = new Set[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new HashSet<>();
        }
        
        //2.初始化邻接表数据，不存在入度数据(构建一个有向图--->树)
        for (int[] edg : edges) {
            adj[edg[0]].add(edg[1]);
            adj[edg[1]].add(edg[0]);
        }
        
        //3.寻找每个节点的深度
        int minDepth = n;
        List<Integer> result = new ArrayList<>();
        for (int root = 0; root < n; root++) {
            int depth = bfs(root, adj, n);
            if (depth < minDepth) {
                minDepth = depth;
                result.clear();
                result.add(root);
            } else if (depth == minDepth) {
                result.add(root);
            }
        }
        return result;
    }
    
    /**
     * 统计每个节点的深度
     *
     * @param root
     * @param adj
     * @param n
     * @return
     */
    public int bfs(int root, Set<Integer>[] adj, int n) {
        //统计层次数
        int depth = 0;
        Queue<Integer> queue = new LinkedList<>();
        //标记是否已经使用过
        boolean[] visited = new boolean[n];
        queue.offer(root);
        visited[root] = true;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) {
                Integer node = queue.poll();
                Set<Integer> set = adj[node];
                for (int next : set) {
                    if (!visited[next]) {
                        queue.offer(next);
                        visited[next] = true;
                    }
                }
            }
        }
        return depth;
    }
    
    /**
     * 两端烧香求中点方法(从边缘向中间遍历)
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTreesByOptimize(int n, int[][] edges) {
        //边界条件判断
        if(edges.length == 0){
            return Arrays.asList(0);
        }
        //1.构建邻接表数据结构
        Set<Integer>[] adj = new Set[n];
        for(int i = 0; i < n; i++){
            adj[i] = new HashSet<>();
        }
        
        //2.初始邻接表数据，并初始化节点入度数据
        int[] inDegree = new int[n];
        for(int[] edge : edges){
            inDegree[edge[0]]++;
            inDegree[edge[1]]++;
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        
        //3.bfs遍历: 寻找顶点的前驱结点(入度为1的节点，作为BFS遍历的起点)
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < n; i++){
            if(inDegree[i] == 1){
                queue.offer(i);
            }
        }
        List<Integer> result = null;
        while(!queue.isEmpty()){
            //记录层次的节点数
            int size = queue.size();
            result = new ArrayList<>();
            for(int i = 0; i < size; i++){
                Integer curNode = queue.poll();
                result.add(curNode);
                for(int next : adj[curNode]){
                    //删除一条边，等价于被指向结点的入度减 1
                    inDegree[next]--;
                    //如果入度减 1 以后为 1，该结点就是接下来要遍历到的结点
                    if(inDegree[next] == 1){
                        queue.offer(next);
                    }
                }
            }
        }
        return result;
    }
}
