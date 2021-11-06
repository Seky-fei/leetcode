package com.seky.leetcode.dynamic;

import org.junit.jupiter.api.Test;

/**
 * @author: wf
 * @create: 2021/8/9 17:44
 * @description:
 *
 * 		 1
 * 		2 4
 * 	   4 7 8
 * 	  1 4 9 10
 * 	 9 6 8 1 10
 *  在上面数字三角形中寻找一条从顶部到底部的路径，使得路径上的数字之和最大(路径上每步只能往左下或右下走)
 *  求出最大和，不必给出路径
 *
 */
public class 经典数字三角形问题 {

    @Test
    public void test(){
        /**
         * 二维数组装三角形数字
         * 1
         * 2 4
         * 4 7 8
         * 1 4 9 10
         * 9 6 8 1 10
         */
        //数组
        int[][] numbers = new int[][]{{1}, {2, 4}, {4, 7, 8}, {1, 4, 9, 10}, {9, 6, 8, 1, 10}};
        int i = 0, j = 0;
        int maxCountPath = findMaxCountPath(i, j, numbers);
        System.out.println(maxCountPath);
    }
    
    /**
     * 
     * 从(i, j)位置处到底部的路径最大值: f(i, j) = max[f(i+1, j), f(i+1, j+1)] + numbers[i][j]
     *      f(i+1, j)是左路径的最大值, f(i+1, j+1)是右逻辑的最大值
     *      
     *  这种解答过程时间复杂度难以想象的大,因为他对有的数字的解进行了多次的重复计算. 
     *  复杂度计算: 2^(n-1)   
     *  
     *  复杂度高的原因: 它对有的数字的解进行了多次的重复计算.
     * 
     * @param i
     * @param j
     * @param numbers
     * @return
     */
    public int findMaxCountPath(int i, int j, int[][] numbers){
        //递归结束语
        if(i >= numbers.length - 1){
            return numbers[i][j];
        }
        int x = findMaxCountPath(i+1, j, numbers);
        int y = findMaxCountPath(i+1, j+1, numbers);
        return Math.max(x, y) + numbers[i][j];
    }
}
