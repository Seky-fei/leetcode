package com.seky.leetcode.BFS.抽象成图论问题;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: wf
 * @create: 2021/10/29 10:52
 * @description:
 */
public class 括号生成 {
    
    @Test
    public void testMath(){
        System.out.println(generateParenthesis(1));
        StringBuilder builder = new StringBuilder();
        
        builder.setLength(0);
    }
    
    /**
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * (有效括号组合需满足：左括号必须以正确的顺序闭合)
     * @param num
     * @return
     */
    public List<String> generateParenthesis(int num){
        List<String> result = new ArrayList<>();
        Queue<Parenthesis> queue = new LinkedList<>();
        queue.offer(new Parenthesis(0, 0, ""));
        while (!queue.isEmpty()){
            Parenthesis p = queue.poll();
            if(p.remainL == num && p.remainR == num){
                result.add(p.symbol);
                continue;
            }
            //左括号分支
            if(num > p.remainL){
                String symbol = new StringBuilder().append(p.symbol).append("(").toString();
                queue.offer(new Parenthesis(p.remainL+1, p.remainR, symbol));
            }
            //右括号分支: 左括号剩余数 小于 右括号剩余数
            if(p.remainL > p.remainR){
                String symbol = new StringBuffer().append(p.symbol).append(")").toString();
                queue.offer(new Parenthesis(p.remainL, p.remainR+1, symbol));
            }
        }
        return result;
    }
    
    class Parenthesis{
        //左括号数
        public int remainL;
        //右括号数
        public int remainR;
        //括号
        public String symbol;
    
        public Parenthesis(int remainL, int remainR, String symbol) {
            this.remainL = remainL;
            this.remainR = remainR;
            this.symbol = symbol;
        }
    }
}
