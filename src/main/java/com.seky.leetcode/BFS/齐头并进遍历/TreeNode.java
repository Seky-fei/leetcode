package com.seky.leetcode.BFS.齐头并进遍历;

/**
 * @author: wf
 * @create: 2021/10/11 11:25
 * @description:  二叉树遍历
 */
public class TreeNode {
    public int val;
    
    public TreeNode left;
    
    public TreeNode right;
    
    public TreeNode(int val) {
        this.val = val;
    }
    
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
