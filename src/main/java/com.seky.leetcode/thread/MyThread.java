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
    
    public static void main(String[] args) throws Exception {
        StringBuilder builder = new StringBuilder();
        //StringBuilder常用方法
        builder.append("来BC");
        System.out.println("1111  " + builder.toString());
        
        builder.appendCodePoint(64); //在末尾追加一个codePoint(参数是ASCII码对应的 整数)
        System.out.println("2222  " + builder.toString());
        
        builder.setCharAt(1, 'A');
        System.out.println("3333  " + builder.toString());
    
        builder.insert(1, 'B'); //insert(int offset, Char c)：在指定位置之前插入字符(串)
        System.out.println("4444  " + builder.toString());
    
    
        builder.delete(1, 2); //删除起始位置（含）到结尾位置（不含）之间的字符串
        System.out.println("5555  " + builder.toString());
        
        System.out.println(builder.charAt(0));
    }
}
