package com.seky.leetcode.BFS.二维平面上的搜索;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: wf
 * @create: 2021/10/15 14:38
 * @description:  四连通问题
 */
public class 岛屿最大面积 {
    
    /**
     * 方向向量（坐标偏移），4 个方向的顺序无关紧要
     */
    private int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    @Test
    public void testMaxArea() {
        //二维数组
        int[][] grid1 = new int[][]{
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };
        
        
        System.out.println(maxAreaOfIsLand(grid1));
        
        
        int[][] grid2 = new int[][]{{0, 0}, {0, 0}, {1, 0}};
        System.out.println(maxAreaOfIsLand(grid2));
    }
    
    
    public int maxAreaOfIsLand(int[][] grid){
        //行,列
        int rows = grid.length;
        int cols = grid[0].length;
        //最大岛屿数
        int maxLand = 0;
        //访问节点标记
        boolean[][] visited = new boolean[rows][cols];
        
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < cols; y++){
                if(!visited[x][y] && grid[x][y] == 1){
                    maxLand = Math.max(maxLand, landBfs(grid, rows, cols, x, y, visited));
                }
            }
        }
        return maxLand;
    }
    
    /**
     * bfs广度优先遍历值为1的节点
     * @param grid
     * @param rows
     * @param cols
     * @param x
     * @param y
     * @param visited
     * @return
     */
    public int landBfs(int[][] grid, int rows, int cols, int x, int y, boolean[][] visited){
        //队列记录已经访问的节点
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        visited[x][y] = true;
        //岛屿个数
        int landCount = 0;
        while(!queue.isEmpty()){
            int [] item = queue.poll();
            //逆向思维：队列里取出多少个节点，就是岛屿的个数(只有值为1的节点才加入队列)
            landCount++;
            for(int[] dire : directions){
                //新节点坐标
                int newX = dire[0] + item[0];
                int newY = dire[1] + item[1];
                //判断新节点是不是岛屿
                boolean isLand = newX < rows && newX >= 0 && newY < cols && newY >= 0 && grid[newX][newY] == 1;
                if(isLand && !visited[newX][newY]){
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
        return landCount;
    }
}
