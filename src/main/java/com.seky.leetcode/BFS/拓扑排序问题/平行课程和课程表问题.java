package com.seky.leetcode.BFS.拓扑排序问题;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author: wf
 * @create: 2021/11/2 18:44
 * @description:
 */
public class 平行课程和课程表问题 {
    
    @Test
    public void testMath() {
        int[][] grids = new int[][]{
                {1, 3}, {2, 3}
        };
        int number = 3;
        System.out.println(minSemesters(number, grids));
        System.out.println("============================================");
    
        int[][] prerequisites = new int[][]{
                {1, 0}, {2, 0}, {3, 1}, {3, 2}
        };
        System.out.println(canFinish(4, prerequisites));
    }
    
    /**
     * 平行课程问题：拓扑排序问题
     * @param n
     * @param relations
     * @return
     */
    public int minSemesters(int n, int[][] relations) {
        //1.初始化邻接表数据结构
        Set<Integer>[] adj = new Set[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new HashSet<>();
        }
        
        //2.构建入度数据结构，并初始化邻节点数据结构
        int[] inDegree = new int[n + 1];
        for (int[] relation : relations) {
            adj[relation[0]].add(relation[1]);
            inDegree[relation[1]]++;
        }
        
        //3.寻找顶点的前驱结点(入度为0的节点，作为BFS遍历的起点)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        //4.BFS遍历进行拓扑排序
        int depth = 0;
        while (!queue.isEmpty()) {
            //记录层次
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) {
                Integer pNode = queue.poll();
                Set<Integer> nextNodes = adj[pNode];
                for (int next : nextNodes) {
                    //删除一条边，等价于被指向结点的入度减 1
                    inDegree[next]--;
                    //如果入度减 1 以后为 0，该结点就是接下来要遍历到的结点
                    if (inDegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
        }
        
        //5.有向图:环形判断()
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] > 0) {
                return -1;
            }
        }
        return depth;
    }
    
    
    
    /**
     * 力扣：第 210 题：课程表（中等）
     * 一个学期必须选修 numCourses 门课，在选修某些课程之前需要一些先修课程。
     * 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程 bi。
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites){
        //1.初始化邻接表数据结构
        Set<Integer>[] adj = new Set[numCourses];
        for(int i = 0; i< numCourses; i++){
            adj[i] = new HashSet<>();
        }
                
        //2.构建入度数据结构，并初始化邻节点数据结构
        int[] inDegree = new int[numCourses];
        for(int[] pre : prerequisites){
            adj[pre[1]].add(pre[0]);
            inDegree[pre[0]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        //3.寻前驱节点(入度为0的数据)
        for(int i = 0; i < numCourses; i++){
            if(inDegree[i] == 0){
                queue.offer(i);
            }
        }
        
        //4.bfs循环遍历
        while(!queue.isEmpty()){
            Integer curNode = queue.poll();
            Set<Integer> nexts = adj[curNode];
            for(Integer next : nexts){
                inDegree[next]--;
                if(inDegree[next] == 0){
                    queue.offer(next);
                }
            }
        }
        
        //5.判断是否有环存在: 本题不涉的数目统计，可以统计压入队列的节点来判断
        for(int v : inDegree){
            if(v > 0){
                return false;
            }
        }
        return true;
    }
}
