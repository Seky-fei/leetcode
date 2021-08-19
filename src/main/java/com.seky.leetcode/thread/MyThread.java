package com.seky.leetcode.thread;

import com.seky.leetcode.entry.Node;

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
        //数据
        Node h = null, q = null;
        Node p = head;
        while(p != null){
            q = p.next;
            p.next = h;
            h = p;
            p = q;
        }
        while (h != null){
            System.out.print(h.val + ",");
            h = h.next;
        }
    }
    
    public Node coverLinkedList(Node node, int begin, int end){
        
        
        return null;
    }
}
