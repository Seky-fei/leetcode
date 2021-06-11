package com.seky.leetcode.thread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: wf
 * @create: 2021/6/8 10:57
 * @description:
 */
public class 多个线程交替执行 {
    
    /**
     * 使用Atomic作为线程间的通信依赖关系
     */
    class MyThread1 extends Thread {
        private Object monitor;
        private AtomicInteger atomicInteger;
        private int tag;
        
        public MyThread1(Object monitor, AtomicInteger atomicInteger, int tag) {
            this.monitor = monitor;
            this.atomicInteger = atomicInteger;
            this.tag = tag;
        }
        
        @Override
        public void run() {
            try {
                synchronized (monitor) {
                    while (true) {
                        if (atomicInteger.get() % 3 == tag) {
                            String name = Thread.currentThread().getName();
                            System.out.println(name + " 线程执行......");
                            Thread.sleep(1000);
                            atomicInteger.incrementAndGet();
                        }
                        monitor.notifyAll();
                        monitor.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void testAtomic() throws InterruptedException {
        Object monitor = new Object();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        List<Thread> list = new ArrayList<>();
        MyThread1 a = new MyThread1(monitor, atomicInteger, 1);
        a.setName("A");
        MyThread1 b = new MyThread1(monitor, atomicInteger, 2);
        b.setName("B");
        MyThread1 c = new MyThread1(monitor, atomicInteger, 0);
        c.setName("C");
        list.add(a);
        list.add(b);
        list.add(c);
        for (Thread thread : list) {
            thread.start();
        }
        Thread.sleep(1000 * 30);
    }
    
    /**
     * 使用volatile作为多个线程间的通信依赖媒介
     */
    private volatile Integer atomicInteger = 1;
    class Thread2 extends Thread{
        Integer tag;
        
        public Thread2(int tag){
            this.tag = tag;
        }
        
        @Override
        public void run() {
            try {
                while(true){
                    if(atomicInteger % 3 == tag){
                        String name = Thread.currentThread().getName();
                        System.out.println(name + " 线程执行。。。。");
                        atomicInteger++;
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void testVolatile() throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        Thread2 a = new Thread2(1);
        a.setName("A");
        Thread2 b = new Thread2(2);
        b.setName("B");
        Thread2 c = new Thread2(0);
        c.setName("C");
        list.add(a);
        list.add(b);
        list.add(c);
        for (Thread thread : list) {
            thread.start();
        }
        Thread.sleep(1000 * 30);
    }
}
