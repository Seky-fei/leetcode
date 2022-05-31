package com.seky.leetcode.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @author: wf
 * @create: 2022/4/7 16:05
 * @description:
 */
public class Consumer {
    
    /**
     * 负载均衡消费模式--集群模式
     */
    public static void clusterConsume() throws Exception {
        // 实例化消息生产者,指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定Namesrv地址信息
        consumer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        // 订阅Topic，消费所有tags(可以用tag过滤消息)
        //consumer.subscribe("test_topic", "*");
        consumer.subscribe("test_topic", "*");
        //默认就是负载均衡模式消费
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        
        // 注册回调函数，处理消息clusterConsumer
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt ext : msgs) {
                String topic = ext.getTopic();
                String threadName = Thread.currentThread().getName();
                String msgId = ext.getMsgId();
                int queueId = ext.getQueueId();
                String body = new String(ext.getBody());
                System.out.println(topic + "  " +threadName + " " + queueId + "  " + msgId + "  " + body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动消息者
        consumer.start();
        System.out.println("Consumer Started。。。。");
    }
    
    /**
     * 广播模式消费
     * @throws Exception
     */
    public static void broadConsume() throws Exception {
        //创建消费者，并指定groupName
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        consumer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        // 订阅Topic，消费所有tags(可以用tag过滤消息)
        consumer.subscribe("test_topic", "*");
        //消费模式：广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //注册回调函数，处理消息
        consumer.registerMessageListener((MessageListenerConcurrently)(msgs, context) -> {
            for(MessageExt ext : msgs){
                long timestamp = ext.getBornTimestamp();
                String msgId = ext.getMsgId();
                String body = new String(ext.getBody());
                System.out.println(msgId + "  " + timestamp + "  " + body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动消费者
        consumer.start();
        System.out.println("consumer start success。。。。。");
    }
    
    /**
     * 顺序消费：限制一个queue只能一个线程消费(多个线程消费一个queue就不能保证顺序性)
     */
    public static void consumerInOrder() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group4");
        consumer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        consumer.subscribe("test_order_topic", "*");
        
        //消费时，限制一个queue只用一个线程进行消费(注册MessageListenerOrderly监听器)
        consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
            for (MessageExt ext : msgs) {
                String threadName = Thread.currentThread().getName();
                String body = new String(ext.getBody());
                String msgId = ext.getMsgId();
                int queueId = ext.getQueueId();
                System.out.println(threadName + " " + queueId + "  " + msgId + "  " + body);
                
                //模拟业务处理
                try {
                    Thread.sleep(1000 * 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        //启动消费者
        consumer.start();
        System.out.println("消费者启动成功！！！");
    }
    
    /**
     * sql表达式过滤消息
     * @throws Exception
     */
    public static void consumerInSql() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        consumer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        consumer.subscribe("test_topic", MessageSelector.bySql("userId BETWEEN 0 AND 2"));
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String body = new String(msg.getBody());
                String tags = msg.getTags();
                System.out.println(tags + "   " + body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println("消费者启动成功！！！");
    }
    
    public static void main(String[] args) throws Exception {
        //集群模式消费
        clusterConsume();
        
        //广播模式消费消息
        //broadConsume();
        
        //顺序消费
        //consumerInOrder();
        
        //sql表达式过滤消息
        //consumerInSql();
    }
}
