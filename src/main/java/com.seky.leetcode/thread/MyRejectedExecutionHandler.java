package com.seky.leetcode.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: wf
 * @create: 2021/11/4 20:31
 * @description:
 */
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if(r instanceof Sender){
            Sender sender = (Sender) r;
            System.out.println("获取任务的消息：" + sender.getMsg());
        }
    }
}
