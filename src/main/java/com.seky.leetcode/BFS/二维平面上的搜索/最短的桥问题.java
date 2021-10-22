package com.seky.leetcode.BFS.二维平面上的搜索;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
        
        int[][] grid1 = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };
    
        int[][] grid2 = new int[][]{
            {0,1,0,0,0},
            {0,1,0,1,1},
            {0,0,0,0,1},
            {0,0,0,0,0},
            {0,0,0,0,0}
        };
        
        System.out.println(sortBridge(grid2));
        System.out.println("================================");
        for (int[] g : grid2) {
            System.out.println(Arrays.toString(g));
        }
        System.out.println("================================");
    }
    
    /**
     * 最短的桥问题分两步: 首先找到这两座岛，随后选择一座，将它不断向外延伸一圈，直到到达了另一座岛。
     *      第一步: bfs遍历找到两个岛屿中的其中一个,并标记好
     *      第二步: 只对标记岛屿上的节点进行bfs向外延伸, 在向外延伸时到达另一个岛屿就结束
     *
     * @param grid
     * @return
     */
    public int sortBridge(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        boolean[][] target = new boolean[rows][cols];
        //寻找其中一个岛屿, 并标记这个岛屿节点值为2
        markOneLand(grid, target);
        
        System.out.println("==========================");
        for (int[] g : grid) {
            System.out.println(Arrays.toString(g));
        }
        System.out.println("==========================");
        
        //bfs寻找路径:只对标记
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        for (int x = 0; x < rows; x++) {
            for(int y = 0; y < cols; y++){
                if(grid[x][y] == 1){
                    queue.offer(new Node(x, y, 0));
                    visited[x][y] = true;
                }
            }
        }
        while (!queue.isEmpty()){
            Node curNode = queue.poll();
            if(target[curNode.x][curNode.y]){
                return curNode.deepPath-1;
            }
            for(int[] dire : directions){
                int newX = dire[0] + curNode.x;
                int newY = dire[1] + curNode.y;
                if(newX < rows && newX >= 0 && newY < cols && newY >= 0 && !visited[newX][newY]){
                    queue.offer(new Node(newX, newY, curNode.deepPath+1));
                    visited[newX][newY] = true;
                }
            }
        }
        return -1;
    }
    
    /**
     * 寻找其中一个岛屿,并标记节点的值为2
     *
     * @param grid
     * @param target
     */
    public void markOneLand(int[][] grid, boolean[][] target) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        
        int label = 0;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (grid[x][y] == 1 && !visited[x][y]) {
                    Queue<Integer> queue = new LinkedList<>();
                    int val = rows*y + x;
                    queue.offer(val);
                    visited[x][y] = true;
                    label++;
                    while (!queue.isEmpty()) {
                        int curVal = queue.poll();
                        int curX = curVal % rows;
                        int curY = curVal/rows;
                        //找到第一个岛屿,并标记岛屿的值为2
                        if (label == 1) {
                            target[curX][curY] = true;
                            grid[curX][curY] = 2;
                        }
                        for (int[] dire : directions) {
                            int newX = dire[0] + curX;
                            int newY = dire[1] + curY;
                            if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && !visited[newX][newY] && grid[newX][newY] == 1) {
                                int v = rows * newY + newX;
                                queue.offer(v);
                                visited[newX][newY] = true;
                            }
                        }
                    }
                }
            }
        }
    }
    
    class Node {
        public Node(int x, int y, int deepPath) {
            this.x = x;
            this.y = y;
            this.deepPath = deepPath;
        }
        
        public int x;
        public int y;
        public int deepPath;
    }
}
