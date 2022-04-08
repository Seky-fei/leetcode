package com.seky.leetcode.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.junit.jupiter.api.Test;

/**
 * @author: wf
 * @create: 2022/4/7 16:05
 * @description:
 */
public class Consumer {
    
    /**
     * 负载均衡消费模式
     */
    @Test
    public void clusterConsume() throws Exception {
        // 实例化消息生产者,指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定Namesrv地址信息
        consumer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        // 订阅Topic，消费所有tags(可以用tag过滤消息)
        consumer.subscribe("test_topic", "*");
        //负载均衡模式消费
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册回调函数，处理消息clusterConsumer
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt ext : msgs) {
                String msgId = ext.getMsgId();
                long bornTimestamp = ext.getBornTimestamp();
                String body = new String(ext.getBody());
                System.out.println(msgId + "  " + bornTimestamp + "  " + body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动消息者
        consumer.start();
        System.out.println("Consumer Started。。。。");
    }
    
    public static void main(String[] args) throws MQClientException {
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
                String msgId = ext.getMsgId();
                long timestamp = ext.getBornTimestamp();
                String body = new String(ext.getBody());
                System.out.println(msgId + "  " + timestamp + "  " + body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动消费者
        consumer.start();
        System.out.println("consumer start success。。。。。");
    }
}
