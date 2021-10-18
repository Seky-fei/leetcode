package com.seky.leetcode.BFS.齐头并进遍历;

import java.util.List;

/**
 * @author: wf
 * @create: 2021/10/13 15:41
 * @description: N叉树结构
 */
public class NTreeNode {
    public Integer val;
    public List<NTreeNode> children;
    
    public NTreeNode(Integer val) {
        this.val = val;
    }
    
    public NTreeNode(Integer val, List<NTreeNode> children) {
        this.val = val;
        this.children = children;
    }
}
