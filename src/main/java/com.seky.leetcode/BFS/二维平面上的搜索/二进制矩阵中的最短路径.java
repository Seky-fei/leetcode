package com.seky.leetcode.BFS.二维平面上的搜索;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: wf
 * @create: 2021/10/18 14:20
 * @description: 八连通问题(跟四连通问题解法类似)
 */
public class 二进制矩阵中的最短路径 {
    
    //方向向量（坐标偏移），8个方向的顺序无关紧要
    public int[][] directions = new int[][]{{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {0, 0}, {1, 0}, {-1, -1}, {0, -1}, {1, -1}};
    
    @Test
    public void testPath() {
        int[][] grid = new int[][]{{0, 0, 0}, {1, 1, 0}, {1, 1, 0}};
        int[][] grid2 = new int[][]{{0,1},{1,0}};
        int[][] grid3 = new int[][]{{0,0,1,0},{1,0,1,0},{1,1,0,1},{0,0,0,0}};
        int[][] grid4 = new int[][]{{0,0,0,0,1},{1,0,0,0,0},{0,1,0,1,0},{0,0,0,1,1},{0,0,0,1,0}};
        
        System.out.println(shortTestPathBinaryMatrix(grid));
        System.out.println(shortTestPathBinaryMatrix(grid2));
        System.out.println(shortTestPathBinaryMatrix(grid3));
        System.out.println(shortTestPathBinaryMatrix(grid4));
        
        System.out.println("==================================");
        System.out.println(shortPathOptimize(grid));
        System.out.println(shortPathOptimize(grid2));
        System.out.println(shortPathOptimize(grid3));
        System.out.println(shortPathOptimize(grid4));
    }
    
    /**
     * 未优化前代码
     * @param grid
     * @return
     */
    public int shortTestPathBinaryMatrix(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        if(grid[0][0] != 0 || grid[rows-1][cols-1] != 0){
            return -1;
        }
        
        int pathCount = 0;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            pathCount++;
            for(int i = 0; i < size; i++){
                int[] curIndex = queue.poll();
                //判断当前层是否已经达到右下角单元格（即，(n - 1, n - 1)）
                if(curIndex[0] == rows -1 && curIndex[1] == cols-1){
                    return pathCount;
                }
                //八个方向遍历
                for (int[] dire : directions) {
                    int newX = dire[0] + curIndex[0];
                    int newY = dire[1] + curIndex[1];
                    boolean isCheck = newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY] == 0;
                    if (isCheck && !visited[newX][newY]) {
                        queue.offer(new int[]{newX, newY});
                        visited[newX][newY] = true;
                    }
                }
            }
        }
        //没有路径达到右下角单元格
        return -1;
    }
    
    /**
     * 八连通问题(优化解法：减少一层循环，直接使用二维数组存已访问的位置)
     * @param grid
     * @return
     */
    public int shortPathOptimize(int[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;
        if(grid[0][0] != 0 || grid[rows - 1][cols-1] != 0){
            return -1;
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        grid[0][0] = 1; //标记位置已经访问过
        //队列长度,pollSize要和队列做对比,知道何时到达队列长度(size记录bfs中每层元素个数)
        int size = queue.size();
        int pollSize = 0;
        //路径:第一个元素为0，path从1开始
        int minPath = 1;
        
        while (!queue.isEmpty()){
            int[] curNode = queue.poll();
            //标记队列取出元素个数
            pollSize++;
            if(curNode[0] == rows-1 && curNode[1] == cols-1){
                return minPath;
            }
            
            for(int[] dire : directions){
                int newX = dire[0] + curNode[0];
                int newY = dire[1] + curNode[1];
                boolean isCheck = newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY] == 0;
                if(isCheck){
                    queue.offer(new int[]{newX, newY}); //满足条件的位置入队
                    grid[newX][newY] = 1; //标记位置已经访问过
                }
            }
            //对比知道何时到达队列长度
            if(size == pollSize){
                size = queue.size();
                pollSize = 0;
                minPath++;
            }
        }
        return -1;
    }
}
