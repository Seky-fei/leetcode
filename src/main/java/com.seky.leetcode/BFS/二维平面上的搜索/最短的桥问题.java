package com.seky.leetcode.BFS.二维平面上的搜索;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: wf
 * @create: 2021/10/21 14:51
 * @description: 平面上四通问题
 */
public class 最短的桥问题 {
    
    /**
     * 方向向量（坐标偏移），4 个方向的顺序无关紧要
     */
    private int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    @Test
    public void testMath() {
        
        int[][] grid = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };
        
    }
    
    
    public int sortBridge(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        //寻找到两个岛屿，标记其中一个岛屿
        boolean[][] target = new boolean[rows][cols];
        //结束后:target是值为2的岛屿, 另一个岛屿值是1
        findTargetLand(grid, target, rows, cols);
        
        //寻找: 选择一座岛屿，将它不断向外延伸一圈，直到到达了另一座岛
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if(grid[x][y] == 1){
                    
                }
            }
        }
        
        
        return -1;
    }
    
    /**
     * @param grid
     * @param target
     */
    public void findTargetLand(int[][] grid, boolean[][] target, int rows, int cols) {
        boolean[][] visited = new boolean[rows][cols];
        int t = 0;
        
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (!visited[x][y] && grid[x][y] == 1) {
                    //start bfs
                    Queue<int[]> queue = new LinkedList<>();
                    queue.offer(new int[]{x, y});
                    visited[x][y] = true;
                    
                    t++;
                    while (!queue.isEmpty()) {
                        int[] curIndex = queue.poll();
                        for (int[] dire : directions) {
                            int newX = dire[0] + curIndex[0];
                            int newY = dire[1] + curIndex[1];
                            if(newX >= 0 && newX < rows && newY >= 0 && newY < cols && !visited[newX][newY] && grid[newX][newY] == 1){
                                queue.offer(new int[]{newX, newY});
                                visited[newX][newY] = true;
                                //标记其中一个岛屿 + 更改值
                                grid[newX][newY] = t;
                                if(t == 2){
                                    target[newX][newY] = true;
                                }
                            }
                        }
                    }
                    
                }
            }
        }
    }
    
    public void bfs(int[][] grid, int[][] target, int x, int y, boolean[][] visited) {
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[][]> queue = new LinkedList<>();
        queue.offer(new int[x][y]);
        visited[x][y] = true;
        
        
    }
}
