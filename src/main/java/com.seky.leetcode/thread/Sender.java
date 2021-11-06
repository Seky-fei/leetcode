package com.seky.leetcode.thread;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * @author: wf
 * @create: 2021/11/4 20:22
 * @description:
 */
@Data
public class Sender implements Runnable{
    private String msg;
    private String name;
    
    public Sender(String msg, String name) {
        this.msg = msg;
        this.name = name;
    }
    
    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Sender start。。。。。。。。");
        Thread.sleep(2000);
        System.out.println("Sender end。。。。。。。。");
    }
}
