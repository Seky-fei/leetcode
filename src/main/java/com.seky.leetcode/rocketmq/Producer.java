package com.seky.leetcode.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.jupiter.api.Test;

/**
 * @author: wf
 * @create: 2022/4/7 15:08
 * @description:
 */
public class Producer {
    
    /**
     * 发送同步消息
     *
     * @throws Exception
     */
    @Test
    public void syncProducer() throws Exception {
        String producerGroup = "sync_product_group";
        //1.实例化消息生产者Producer,指定groupName
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        //2.设置NameServer的地址
        producer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        //3.启动Producer实例
        producer.start();
        for (int i = 1; i <= 1; i++) {
            //4.创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("test_topic", "TagA", ("发送同步消息 " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            //通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        //如果不再发送消息，关闭Producer实例
        producer.shutdown();
    }
    
    /**
     * 发送异步消息
     *
     * @throws Exception
     */
    @Test
    public void asyncProducer() throws Exception {
        //1.实例化消息生产者,并指定groupName
        DefaultMQProducer producer = new DefaultMQProducer("async_product_group");
        //2.设置NameServer的地址
        producer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        //3.启动producer
        producer.start();
        for(int i = 1; i <= 2; i++){
            Message msg = new Message("test_topic", "TagB", ("发送异步消息" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //4.发送异步消息,异步返回结果
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送异步消息成功！" + sendResult);
                }
            
                @Override
                public void onException(Throwable e) {
                    System.out.println("发送异步消息失败！" + e.toString());
                    e.printStackTrace();
                }
            });
        }
        //注意:异步发送消息时，异步返回结果需要时间，不然会报错
        Thread.sleep(1000 * 3);
        //5.关闭Producer实例
        producer.shutdown();
    }
    
    /**
     * 发送单向消息：只负责发送，不关心发送后的结果
     */
    @Test
    public void oneWayProducer() throws Exception {
        //实例化消息生产者,并指定groupName
        DefaultMQProducer producer = new DefaultMQProducer("oneway_product_group");
        //设置NameServer的地址
        producer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        producer.start();
        for(int i = 1; i <= 20; i++){
            Message msg = new Message("test_topic", "TagC", ("发送单向消息" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送单向消息，没有任何返回值
            producer.sendOneway(msg);
        }
        //关闭producer
        producer.shutdown();
    }
    
    /**
     * 发送顺序消息
     */
    @Test
    public void orderMsgProducer(){
        
    }
}
