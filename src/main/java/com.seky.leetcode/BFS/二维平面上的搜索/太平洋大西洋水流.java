package com.seky.leetcode.BFS.二维平面上的搜索;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: wf
 * @create: 2021/10/19 16:09
 * @description: 太平洋大西洋水流问题
 */
public class 太平洋大西洋水流 {
    //太平洋方向坐标(左上)
    public int[][] pacificDirections = new int[][]{{-1, 0}, {0, 1}};
    
    //大西洋方向坐标(右下)
    public int[][] atlanticDirections = new int[][]{{1, 0}, {0, -1}};
    
    public int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    
    @Test
    public void testMath(){
        int[][] grid = new int[][]{
                {1, 2, 2, 3, 5},
                {3, 2, 3, 4, 4},
                {2, 4, 5, 3, 1},
                {6, 7, 1, 4, 5},
                {5, 1, 1, 2, 4}
        };
        
        System.out.println(pacificAtlantic(grid));
    }
    
    
    public List<List<Integer>> pacificAtlantic(int[][] grid){
        List<List<Integer>> rt = new ArrayList<>();
        int rows = grid.length;
        int cols = grid[0].length;
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < cols; y++){
                boolean checkArrive = bfs(grid, x, y, rows, cols);
                if(checkArrive){
                    List<Integer> index = new ArrayList<>();
                    index.add(x);
                    index.add(y);
                    rt.add(index);
                }
            }
        }
        return rt;
    }
    
    public boolean bfs(int[][] grid, int x, int y, int rows, int cols){
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        queue.offer(new int[]{x, y});
        visited[x][y] = true;
        
        //太平洋方向标记
        boolean isPacific = false;
        //大西洋方向标记
        boolean isAtlantic = false;
        
        while (!queue.isEmpty()){
            int[] curNode = queue.poll();
            int curX = curNode[0];
            int curY = curNode[1];
            if(curX == 0 || curY == 0){
                isPacific = true;
            }
            if(curX == rows-1 || curY == cols-1){
                isAtlantic = true;
            }
            //遍历方向坐标
            for(int[] dire : directions){
                int newX = dire[0] + curX;
                int newY = dire[1] + curY;
                boolean isCheck = newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY] <= grid[curX][curY];
                if(isCheck && !visited[newX][newY]){
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
        return isPacific && isAtlantic;
    }
}
