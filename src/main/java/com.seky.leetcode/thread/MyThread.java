package com.seky.leetcode.thread;

import com.seky.leetcode.entry.Node;

import java.util.LinkedList;

/**
 * @author: wf
 * @create: 2021/6/10 13:48
 * @description:
 */
public class MyThread extends Thread{
    //对象监听器
    private Object monitor;
    public MyThread(Object monitor){
        this.monitor = monitor;
    }
    @Override
    public void run() {
        synchronized (monitor){
            try { 
                while(true){
                    String name = Thread.currentThread().getName();
                    System.out.println(name + " 线程执行........");
                    Thread.sleep(1000);
                    //使用notifyAll()和wait()实现交替打印
                    monitor.notifyAll();
                    monitor.wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        Node tail = null, head = null;
        for (int i = 1; i <= 5; i++){
            Node next = new Node();
            next.val = i;
            if(tail == null){
                head = next;
                tail = next;
            }else {
                tail.next = next;
                tail = next;
            }
        }
        
        Node temp = head;
        while (temp != null){
            System.out.print(temp.val + ",");
            temp = temp.next;
        }
        System.out.println();
        System.out.println();
        System.out.println();
        Node node = coverLinkedList(head, 2, 4);
        while (node != null){
            System.out.print(node.val + ",");
            node = node.next;
        }
    }
    
    public static Node coverLinkedList(Node node, int n, int m){
        Node head = new Node();
        head.next = node;
        Node pre = node;
        int c = m - n;
        
        while(pre.next != null && n-1 > 1){
            pre = pre.next;
            n--;
        }
        
        //指定区域反转
        Node th = pre.next;
        while(th.next != null && c >= 1){
            Node temp = th.next;
            th.next = temp.next;
            temp.next = pre.next;
            pre.next = temp;
            c--;
        }
        return head.next;
    }
    
    public void linkedList(){
        LinkedList<Integer> linkedList = new LinkedList<>();
        for(int item : linkedList){
            System.out.println(item);
        }
    }
}
