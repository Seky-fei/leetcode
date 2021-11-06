package com.seky.leetcode.BFS.二维平面上的搜索;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: wf
 * @create: 2021/10/20 14:36
 * @description: 「力扣」第 130 题：被围绕的区域（中等） + 「力扣」第 1020 题：飞地的数量（中等）
 */
public class 被围绕的区域和飞地的数量 {
    
    //方向向量（坐标偏移），4 个方向的顺序无关紧
    public int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    
    /**
     * 被围绕的区域()
     */
    @Test
    public void testPackageMath() {
        String[][] grid = new String[][]{
                {"X", "X", "X", "X"},
                {"X", "O", "O", "X"},
                {"X", "X", "O", "X"},
                {"X", "O", "X", "X"}
        };
        
        System.out.println(grid.length);
        
        //替换
        solveByViolence(grid);
        //solveOptimize(grid);
        for (String[] array : grid) {
            System.out.println(Arrays.toString(array));
        }
        System.out.println("111111111111111111111111111111111");
        
        String[][] grid2 = new String[][]{
                {"O", "X", "X", "O", "X"},
                {"X", "O", "O", "X", "O"},
                {"X", "O", "X", "O", "X"},
                {"O", "X", "O", "O", "O"},
                {"X", "X", "O", "X", "O"}
        };
        //优化后接口
        solveOptimize(grid2);
        for (String[] array : grid2) {
            System.out.println(Arrays.toString(array));
        }
    }
    
    /**
     * 飞地的数量：
     * （1）从边界开始fbs遍历，找到能到达边界额陆地，并标记为2
     * （2）遍历二维数组，找出值为1的数据，就是不能到达边界的陆地
     *  (注意：bfs标记)
     */
    @Test
    public void testFlyLandMath() {
        //二维数组
        /*int[][] grid = new int[][]{
                {0, 0, 0, 0},
                {1, 0, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };*/
        int[][] grid = new int[][]{{0,1,1,0},{0,0,1,0},{0,0,1,0},{0,0,0,0}};
        
        //方向
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        //x从0 ---> rows
        for (int x = 0; x < rows; x++) {
            fbsByFlyLand(x, 0, grid, rows, cols);
            fbsByFlyLand(x, cols - 1, grid, rows, cols);
        }
        //y从0 ---> cols
        for (int y = 0; y < cols; y++) {
            fbsByFlyLand(0, y, grid, rows, cols);
            fbsByFlyLand(rows - 1, y, grid, rows, cols);
        }
        for(int[] g : grid){
            System.out.println(Arrays.toString(g));
        }
        //统计数据
        int landCount = 0;
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < cols; y++){
                if(grid[x][y] == 1)
                    landCount++;
            }
        }
        System.out.println("无法到达边界的陆地：" + landCount);
    }
    
    /**
     * 像这种飞地的数量，被围绕的区域 可以用染色代替visited标记节点已经用过,节省不少内存空间
     * @param x
     * @param y
     * @param grid
     * @param rows
     * @param cols
     */
    public void fbsByFlyLand(int x, int y, int[][] grid, int rows, int cols) {
        if (grid[x][y] != 1) {
            return;
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        grid[x][y] = 0;
        while(!queue.isEmpty()){
            int[] curNode = queue.poll();
            for(int[] dire : directions){
                int nextX = curNode[0] + dire[0];
                int nextY = curNode[1] + dire[1];
                if(nextX >=0 && nextX < rows && nextY >= 0 && nextY < cols && grid[nextX][nextY] == 1){
                    queue.offer(new int[]{nextX, nextY});
                    grid[nextX][nextY] = 0;
                }
            }
        }
    }
    
    /**
     * 遍历每个为“O”的节点，判断是否该节点在内的“O”区域是否与边界相连（提交会超时, 暴力解法）
     *
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
     * 被围绕的区域
     * 对每个节点进行-广度优先遍历，判断节点是否满足被'X'围绕的区域
     *
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
        
        
        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int curX = curNode[0];
            int curY = curNode[1];
            //剔除不满足条件的区域 ---》 为“O”的区域有一个节点在边界上
            if (curX == 0 || curX == rows - 1 || curY == 0 || curY == cols - 1) {
                return false;
            }
            //遍历方向向量
            for (int[] dire : directions) {
                int newX = curX + dire[0];
                int newY = curY + dire[1];
                //临界条件判断
                boolean checked = newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY].equals("O");
                if (checked && !visited[newX][newY]) {
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
        return true;
    }
    
    
    /**
     * 被围绕的区域
     * <p>
     * bfs遍历每个为“O”的节点，再判断是否与边界相连 ----> 优化：bfs只遍历边界上为"O"的节点，找到所有与边界相连的“O”，将值替换成A，
     * 最后遍历一遍二维数组把: O ---> X, A --> O即可
     *
     * @param grid
     */
    public void solveOptimize(String[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        boolean[][] visited = new boolean[rows][cols];
        //y=0 和 y=cols-1边
        for (int x = 0; x < rows; x++) {
            //y=0边
            if (!visited[x][0] && grid[x][0].equals("O")) {
                bfsByOptimize(grid, x, 0, rows, cols, visited);
            }
            //y=cols-1边
            if (!visited[x][cols - 1] && grid[x][cols - 1].equals("O")) {
                bfsByOptimize(grid, x, cols - 1, rows, cols, visited);
            }
        }
        
        //x=0边 和 x=rows-1边
        for (int y = 0; y < cols; y++) {
            //x=0边
            if (!visited[0][y] && grid[0][y].equals("O")) {
                bfsByOptimize(grid, 0, y, rows, cols, visited);
            }
            //x=rows-1边
            if (!visited[rows - 1][y] && grid[rows - 1][y].equals("O")) {
                bfsByOptimize(grid, rows - 1, y, rows, cols, visited);
            }
        }
        
        //最后替换:O ---> X, A --> O即可
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (grid[x][y].equals("O")) {
                    grid[x][y] = "X";
                }
                if (grid[x][y].equals("A")) {
                    grid[x][y] = "O";
                }
            }
        }
        
    }
    
    /**
     * 被围绕的区域：边界为"O"的节点执行bfs遍历,并标记为A
     *
     * @param grid
     * @param x
     * @param y
     * @param rows
     * @param cols
     * @param visited
     */
    public void bfsByOptimize(String[][] grid, int x, int y, int rows, int cols, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        visited[x][y] = true;
        
        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int curX = curNode[0];
            int curY = curNode[1];
            
            //所有与边界相连的“O”，将值替换成A，
            grid[curX][curY] = "A";
            //遍历方向向量
            for (int[] dire : directions) {
                int newX = curX + dire[0];
                int newY = curY + dire[1];
                //临界条件判断
                boolean checked = newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY].equals("O");
                if (checked && !visited[newX][newY]) {
                    queue.offer(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
    }
    
    
}
