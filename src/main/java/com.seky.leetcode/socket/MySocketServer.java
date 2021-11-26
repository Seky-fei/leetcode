package com.seky.leetcode.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: wf
 * @create: 2021/11/19 15:45
 * @description:
 */
public class MySocketServer {
    private AtomicInteger atomicInteger = new AtomicInteger();
    
    public static void main(String[] args) throws InterruptedException {
        MySocketServer mySocketServer = new MySocketServer();
        mySocketServer.acceptManager();
        Thread.sleep(1000 * 60);
    }
    
    public void acceptManager() {
        ServerSocket server;
        try {
            server = new ServerSocket(1234);
            while (true) {
                Socket client = server.accept();
                System.out.println("接收到客户端请求！！！");
                new Thread(new SSocket(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    class SSocket implements Runnable{
        private Socket client;
    
        public SSocket(Socket client) {
            this.client = client;
        }
    
        @Override
        public void run() {
            try {
                String name = Thread.currentThread().getName();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("服务端--读取到客户端数据： " + reader.readLine());
                
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                String desc = name + "服务端写入数据！" + atomicInteger.incrementAndGet() + "\n";
                writer.write(desc);
                writer.flush();
                
                Thread.sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
