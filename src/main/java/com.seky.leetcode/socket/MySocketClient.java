package com.seky.leetcode.socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wf
 * @create: 2021/11/19 16:21
 * @description:
 */
public class MySocketClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<Thread> list = new ArrayList<>();
        for(int i = 1; i <= 4; i++){
            Socket socket = new Socket("127.0.0.1", 1234);
            Thread thread = new Thread(new ClientManage(socket));
            list.add(thread);
        }
        
        list.stream().forEach(f -> f.start());
    
        
        Thread.sleep(1000 * 60);
    }
    
    
    static class ClientManage implements Runnable{
        private Socket client;
    
        public ClientManage(Socket client) {
            this.client = client;
        }
    
        @Override
        public void run() {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                String dec = Thread.currentThread().getName() + "来自客户端的问候！！！\n";
                writer.write(dec);
                writer.flush();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("客户端---" + Thread.currentThread().getName() + "  "+ reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
