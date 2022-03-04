package com.seky.leetcode.thread;

import java.util.Hashtable;

/**
 * @author: wf
 * @create: 2022/1/5 18:19
 * @description:
 */
public class PrioritySetThread extends Thread{
    
    private int priorityCount;
    private int priority;
    
    public PrioritySetThread(Integer priority) {
        this.priority = priority;
        this.setName("线程" + priority);
    }
    
    @Override
    public void run(){
        while (true){
            this.priorityCount++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void main(String[] args) {
        Hashtable<Integer, Integer> map = new Hashtable();
        map.put(10, 10);
        
        map.get(10);
        map.remove(10);
    }
}
