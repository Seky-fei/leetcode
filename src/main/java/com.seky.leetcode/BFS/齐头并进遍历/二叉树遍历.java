package com.seky.leetcode.BFS.齐头并进遍历;

import java.util.*;

/**
 * @author: wf
 * @create: 2021/10/13 15:37
 * @description:
 */
public class 二叉树遍历 {
    
    public static void main(String[] args) {
        //数据
        TreeNode node15 = new TreeNode(15);
        TreeNode node7 = new TreeNode(7);
        TreeNode node10 = new TreeNode(10);
        
        TreeNode node9 = new TreeNode(9, node10, null);
        TreeNode node20 = new TreeNode(20, node15, node7);
        
        TreeNode root = new TreeNode(3, node9, node20);
        
        
        System.out.println(levelBottomOrder(root));
        System.out.println(treeLevelOrder(root));
        System.out.println(zigzagLevelOrder1(root));
        System.out.println(zigzagLevelOrder2(root));
        System.out.println("=======================================");
        System.out.println("堂兄弟节点判断：" + isCousinsNode1(root, 15, 7));
        System.out.println("堂兄弟节点判断：" + isCousinsNode2(root, 15, 7));
    }
    
    
    /**
     * 层次遍历二叉树
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        if(root == null){
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int currentSize = queue.size();
            List<Integer> list = new ArrayList<>();
            for(int i = 1; i <= currentSize; i++){
                TreeNode node = queue.poll();
                list.add(node.val);
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
            result.add(list);
        }
        return result;
    }
    
    /***
     * 给定一个二叉树，返回其节点值自底向上的层序遍历。（即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     * @param root
     * @return
     */
    public static List<List<Integer>> levelBottomOrder(TreeNode root){
        List<List<Integer>> rt = new ArrayList<>();
        if(root == null){
            return rt;
        }
        //第一步：BFS搜索遍历，树从上往下
        Queue<TreeNode> queue = new LinkedList<>();
        //root节点入队
        queue.offer(root);
        while (!queue.isEmpty()){
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();
            for(int i = 0; i < size; i++){
                TreeNode node = queue.poll();
                temp.add(node.val);
                if(node.right != null){
                    queue.offer(node.right);
                }
                if(node.left != null){
                    queue.offer(node.left);
                }
            }
            rt.add(temp);
        }
        //第二步：数组翻转
        int left = 0;
        int right = rt.size() - 1;
        while (left < right){
            List<Integer> temp = rt.get(left);
            rt.set(left, rt.get(right));
            rt.set(right, temp);
            left++;
            right--;
        }
        return rt;
    }
    
    /**
     * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印
     * @param root
     * @return
     */
    public static List<Integer> treeLevelOrder(TreeNode root) {
        List<Integer> rt = new ArrayList<>();
        if(root == null){
            return rt;
        }
        //root节点入队
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            //记录当前层节点数
            int size = queue.size();
            TreeNode node = queue.poll();
            rt.add(node.val);
            for(int i = 0; i < size; i++){
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
        }
        return rt;
    }
    
    /**
     * 给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）
     * @return
     */
    public static List<List<Integer>> zigzagLevelOrder1(TreeNode root){
        List<List<Integer>> rt = new ArrayList<>();
        if(root == null){
            return rt;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean tag = true;
        while(!queue.isEmpty()){
            List<Integer> levelList = new ArrayList<>();
            int size = queue.size();
            for(int i = 0; i < size; i++){
                TreeNode node = queue.poll();
                levelList.add(node.val);
                putSubNodeQueue(queue, node, tag);
            }
            tag = tag ? false : true;
            rt.add(levelList);
        }
        return rt;
    }
    public static void putSubNodeQueue(Queue<TreeNode> queue, TreeNode node, boolean tag){
        if(tag){
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }else {
            if(node.right != null){
                queue.offer(node.right);
            }
            if(node.left != null){
                queue.offer(node.left);
            }
        }
    }
    
    /**
     * 二叉树的锯齿形层序遍历(队列维护)
     *  给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）
     * @param root
     */
    public static List<List<Integer>> zigzagLevelOrder2(TreeNode root){
        List<List<Integer>> rt = new ArrayList<>();
        if(root == null){
            return rt;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean isLeft = true; //记录是从左至右还是从右至左的
        while(!queue.isEmpty()){
            int currentSize = queue.size();
            LinkedList<Integer> levelQueue = new LinkedList<>();
            for(int i = 0; i < currentSize; i++){
                TreeNode node = queue.poll();
                //利用双端队列的特性：从左至右 --> 队列尾部，从右至左 --> 队列的头部
                if(isLeft){
                    levelQueue.addFirst(node.val);
                }else {
                    levelQueue.addLast(node.val);
                }
                //添加子节点到队列中
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
            isLeft = !isLeft;
            rt.add(levelQueue);
        }
        return rt;
    }
    
    /**
     * 力扣【993】. 二叉树的堂兄弟节点 (如果二叉树的两个节点深度相同，但 父节点不同 ，则它们是一对堂兄弟节点。)
     *      
     * @param root
     * @param x
     * @param y
     * @return
     */
    public static boolean isCousinsNode1(TreeNode root, int x, int y){
        if(root == null){
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            boolean tagx = false;
            boolean tagy = false;
            int curSize = queue.size();
            
            //如果二叉树的两个节点深度相同，但父节点不同 ，则它们是一对堂兄弟节点
            for(int i = 0; i < curSize; i++){
                TreeNode node = queue.poll();
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
                if(node.val == x){
                    tagx = true;
                }
                if(node.val == y){
                    tagy = true;
                }
                
                //去掉兄弟节点
                if(node.left != null && node.right != null && ((node.left.val == x && node.right.val == y) || (node.left.val == y && node.right.val == x))){
                    return false;
                }
            }
            //堂兄弟节点
            if(tagx & tagy){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 力扣【993】. 二叉树的堂兄弟节点 (如果二叉树的两个节点深度相同，但 父节点不同 ，则它们是一对堂兄弟节点。)
     * @param root
     * @param x  
     * @param y 
     * @return     使用变量分别标记节点值等于x和y时，depth、parentNode
     */
    public static boolean isCousinsNode2(TreeNode root, int x, int y){
        if(root == null){
            return false;
        }
        //x节点标记
        int depthx = 0;
        TreeNode pNodex = null;
        
        //y节点标记
        int depthy = 0;
        TreeNode pNodey = null;
    
        int depth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            int cuSize = queue.size();
            depth++;
            for(int i = 0; i < cuSize; i++){
                TreeNode node = queue.poll();
                if(node.left != null){
                    queue.offer(node.left);
                    if(node.left.val == x){
                        depthx = depth;
                        pNodex = node;
                    }
                    if(node.left.val == y){
                        depthy = depth;
                        pNodey = node;
                    }
                }
                if(node.right != null){
                    queue.offer(node.right);
                    if(node.right.val == x){
                        depthx = depth;
                        pNodex = node;
                    }
                    if(node.right.val == y){
                        depthy = depth;
                        pNodey = node;
                    }
                }
            }
        }
        return (depthx == depthy) & (pNodex != pNodey);
    }
}
