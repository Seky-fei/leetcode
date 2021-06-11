package com.seky.leetcode.thread;

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
    public static void main(String[] args) throws InterruptedException {
        System.out.println(-3>>>2);
    }
}
