package com.seky.leetcode.BFS.二维平面上的搜索;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author: wf
 * @create: 2021/10/20 14:36
 * @description: 「力扣」第 130 题：被围绕的区域（中等）
 */
public class 被围绕的区域 {
    
    //方向向量（坐标偏移），4 个方向的顺序无关紧
    public int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    @Test
    public void testMath() {
    
        Stack<Integer> stack = new Stack<>();
        
        String[][] grid = new String[][]{
                {"X", "X", "X", "X"},
                {"X", "O", "O", "X"},
                {"X", "X", "O", "X"},
                {"X", "O", "X", "X"}
        };
        
        System.out.println(grid.length);
        
        //替换
        //solveByViolence(grid);
        solveOptimize(grid);
        for (String[] array : grid) {
            System.out.println(Arrays.toString(array));
        }
        System.out.println("111111111111111111111111111111111");
        
        String[][] grid2 = new String[][]{
            {"O","X","X","O","X"},
            {"X","O","O","X","O"},
            {"X","O","X","O","X"},
            {"O","X","O","O","O"},
            {"X","X","O","X","O"}
        };
        //solveByViolence(grid2);
        solveOptimize(grid2);
        for (String[] array : grid2) {
            System.out.println(Arrays.toString(array));
        }
    }
    
    /**
     * 遍历每个为“O”的节点，判断是否该节点在内的“O”区域是否与边界相连（提交会超时, 暴力解法）
     * @param grid
     */
    public void solveByViolence(String[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                String item = grid[x][y];
                if (item.equals("O") && bfsByViolence(grid, x, y, rows, cols)) {
                    grid[x][y] = "X";
                }
            }
        }
    }
    
    /**
     * 对每个节点进行-广度优先遍历，判断节点是否满足被'X'围绕的区域
     * @param grid
     * @param x
     * @param y
     * @param rows
     * @param cols
     * @return
     */
    public boolean bfsByViolence(String[][] grid, int x, int y, int rows, int cols) {
        //队列+标记
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        boolean[][] visited = new boolean[rows][cols];
        visited[x][y] = true;
        
        
        while (!queue.isEmpty()){
            int[] curNode = queue.poll();
            int curX = curNode[0];
            int curY = curNode[1];
            //剔除不满足条件的区域 ---》 为“O”的区域有一个节点在边界上
            if(curX == 0 || curX == rows-1 || curY == 0 || curY == cols-1){
                return false;
            }
            //遍历方向向量
            for(int[] dire : directions){
                int newX = curX + dire[0];
                int newY = curY + dire[1];
                //临界条件判断
                boolean checked = newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY].equals("O");
                if(checked && !visited[newX][newY]){
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
        return true;
    }
    
    
    /**
     * bfs遍历每个为“O”的节点，再判断是否与边界相连 ----> 优化：bfs只遍历边界上为"O"的节点，找到所有与边界相连的“O”，将值替换成A，
     *                                                  最后遍历一遍二维数组把: O ---> X, A --> O即可
     * @param grid
     */
    public void solveOptimize(String[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;
        
        boolean[][] visited = new boolean[rows][cols];
        //y=0 和 y=cols-1边
        for(int x = 0; x < rows; x++){
            //y=0边
            if(!visited[x][0] && grid[x][0].equals("O")){
                bfsByOptimize(grid, x, 0, rows, cols, visited);
            }
            //y=cols-1边
            if(!visited[x][cols-1] && grid[x][cols-1].equals("O")){
                bfsByOptimize(grid, x, cols-1, rows, cols, visited);
            }
        }
        
        //x=0边 和 x=rows-1边
        for(int y = 0; y < cols; y++){
            //x=0边
            if(!visited[0][y] && grid[0][y].equals("O")){
                bfsByOptimize(grid, 0, y, rows, cols, visited);
            }
            //x=rows-1边
            if(!visited[rows-1][y] && grid[rows-1][y].equals("O")){
                bfsByOptimize(grid, rows-1, y, rows, cols, visited);
            }
        }
        
        //最后替换:O ---> X, A --> O即可
        for (int x = 0; x < rows; x++){
            for(int y = 0; y < cols; y++){
                if(grid[x][y].equals("O")){
                    grid[x][y] = "X";
                }
                if(grid[x][y].equals("A")){
                    grid[x][y] = "O";
                }
            }
        }
        
    }
    
    /**
     * 边界为"O"的节点执行bfs遍历,并标记为A
     * @param grid
     * @param x
     * @param y
     * @param rows
     * @param cols
     * @param visited
     */
    public void bfsByOptimize(String[][] grid, int x, int y, int rows, int cols, boolean[][] visited){
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        visited[x][y] = true;
        
        while (!queue.isEmpty()){
            int[] curNode = queue.poll();
            int curX = curNode[0];
            int curY = curNode[1];
            
            //所有与边界相连的“O”，将值替换成A，
            grid[curX][curY] = "A";
            //遍历方向向量
            for(int[] dire : directions){
                int newX = curX + dire[0];
                int newY = curY + dire[1];
                //临界条件判断
                boolean checked = newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY].equals("O");
                if(checked && !visited[newX][newY]){
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
    }
}
