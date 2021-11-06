package com.seky.leetcode.BFS.齐头并进遍历;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: wf
 * @create: 2021/10/13 15:57
 * @description: N 叉树的层序遍历
 */ 
public class N叉树的层序遍历 {
    
    @Test
    public void testNTreeOrder(){
        //数据初始化
        NTreeNode node4_1 = new NTreeNode(3);
        NTreeNode node4_2 = new NTreeNode(4);
        NTreeNode node4_3 = new NTreeNode(5);
        List<NTreeNode> level4 = new ArrayList<>();
        level4.add(node4_1);
        level4.add(node4_2);
        level4.add(node4_3);
        
        NTreeNode node3_1 = new NTreeNode(2);
        NTreeNode node3_2 = new NTreeNode(7, level4);
        NTreeNode node3_3 = new NTreeNode(11);
        List<NTreeNode> level3 = new ArrayList<>();
        level3.add(node3_1);
        level3.add(node3_2);
        level3.add(node3_3);
        
        NTreeNode node2_1 = new NTreeNode(9, level3);
        NTreeNode node2_2 = new NTreeNode(8);
        List<NTreeNode> level2 = new ArrayList<>();
        level2.add(node2_1);
        level2.add(node2_2);
    
        NTreeNode root = new NTreeNode(3, level2);
    
    
        System.out.println(nTreeLevelOrder(root));
    }
    
    /**
     * 给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
     * 
     * @param root
     * @return
     */
    public List<List<Integer>> nTreeLevelOrder(NTreeNode root){
        List<List<Integer>> rt = new ArrayList<>();
        if(root == null){
            return rt;
        }
        LinkedList<NTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int curSize = queue.size();
            List<Integer> curList = new ArrayList<>();
            for(int i = 0; i < curSize; i++){
                NTreeNode node = queue.poll();
                curList.add(node.val);
                //判断子节点是否为null
                if(node.children != null){
                    queue.addAll(node.children);
                }
            }
            rt.add(curList);
        }
        return rt;
    }
}
