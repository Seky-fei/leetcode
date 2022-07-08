package com.seky.leetcode.rocketmq;

import com.seky.leetcode.entry.OrderStep;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
    public static void syncProducer() throws Exception {
        String producerGroup = "sync_product_group";
        //1.实例化消息生产者Producer,指定groupName
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        //2.设置NameServer的地址
        producer.setNamesrvAddr("10.30.130.127:9876");
        //设置消息的大小(默认4M)
        //producer.setMaxMessageSize(1024 * 1024 * 6);
        
        //3.启动Producer实例
        producer.start();
        for (int i = 1; i <= 10; i++) {
            //4.创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("test_topic", "TagA", ("发送同步消息2 " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            //通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
            System.out.println("消息体：" + msg);
        }
        //如果不再发送消息，关闭Producer实例
        producer.shutdown();
    }
    
    private static byte[] getByteBody() throws UnsupportedEncodingException {
        byte[] bytes1 = "发送消息：哈啊哈哈".getBytes("UTF-8");
        byte[] bytes2 = new byte[1024 * 1024 * 1];
        byte[] data = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, data, 0, bytes1.length);
        System.arraycopy(bytes2, 0, data, bytes1.length, bytes2.length);
        return data;
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
        for (int i = 1; i <= 2; i++) {
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
        for (int i = 1; i <= 20; i++) {
            Message msg = new Message("test_topic", "TagC", ("发送单向消息" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送单向消息，没有任何返回值
            producer.sendOneway(msg);
        }
        //关闭producer
        producer.shutdown();
    }
    
    /**
     * 发送顺序消息：同一个订单的消息，发送到一个queue中
     */
    public static void orderMsgProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("order_product_group");
        producer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        producer.start();
        //构建订单消息: 每个订单消息都有创建、付款、推送、完成四条消息
        List<OrderStep> orderSteps = OrderStep.buildOrders();
        
        for (int i = 0; i < orderSteps.size(); i++) {
            OrderStep order = orderSteps.get(i);
            String body = String.format("%s---%s", order.getOrderId(), order.getDesc());
            Message message = new Message("test_order_topic", "TagA", body.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //queue选择器：根据订单id选择发送queue，相同的订单发送到同一个queue中，实现顺序发送
            producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    long orderId = (Long) arg;
                    int index = (int) (orderId % mqs.size());
                    return mqs.get(index);
                }
            }, order.getOrderId());
            producer.send(message, (mqs, msg, arg) -> {
                long orderId = (Long) arg;
                int index = (int) (orderId % mqs.size());
                return mqs.get(index);
            }, order.getOrderId());
        }
        System.out.println("发送顺序消息完成！！！");
        producer.shutdown();
    }
    
    /**
     * 发送延迟消息：delayTimeLevel共18个等级，指定延迟消费时间
     */
    public static void delayMsgProducer() throws Exception {
        //初始化生产者
        DefaultMQProducer producer = new DefaultMQProducer("delay_product_group");
        producer.setNamesrvAddr("10.30.130.127:9876");
        producer.start();
        //发送延迟消息
        for (int i = 0; i < 5; i++) {
            String body = "发送延迟消息 " + i;
            Message message = new Message("test_topic", body.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);
            //发送消息
            SendResult result = producer.send(message);
            System.out.println(result);
        }
        //关闭生产者
        producer.shutdown();
    }
    
    /**
     * 发送批量消息
     */
    public static void batchMsgProducer() throws Exception {
        //初始化生产者
        DefaultMQProducer producer = new DefaultMQProducer("batch_product_group");
        producer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        producer.start();
        
        //批量消息
        List<Message> msgList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Message message = new Message("test_topic", "TagA", ("批量消息" + i).getBytes("UTF-8"));
            System.out.println(message.getBody().length);
            msgList.add(message);
        }
        producer.send(msgList);
        //关闭生产者
        producer.shutdown();
    }
    
    /**
     * 发送设置用户属性的消息 ---> 消费者sql表达式过滤消息
     */
    public static void sendUserPropertyMsg() throws Exception {
        //初始化生产者
        DefaultMQProducer producer = new DefaultMQProducer("product_group");
        producer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC"};
        int temp = 1;
        for (String tag : tags) {
            Message message = new Message("test_topic", tag, "这是一个SS公司".getBytes("UTF-8"));
            //设置消息的属性，可以设置多个属性
            message.putUserProperty("userId", String.valueOf(temp++));
            SendResult send = producer.send(message);
            System.out.println(send);
        }
        //关闭生产者
        producer.shutdown();
    }
    
    /**
     * 发送事务消息：正常事务消息的发送及提交 + 事务消息的补偿
     */
    public static void sendTransactionMsg() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        producer.setNamesrvAddr("192.168.33.1:9876;192.168.33.2:9876");
        
        //生产者设置事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 执行本地事务方法
             * @param msg
             * @param arg 发送事务消息时传入的参数
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println( arg + " " + msg.getTags() + "  " + new String(msg.getBody()));
                String tags = msg.getTags();
                if("TagA".equals(tags)){
                    //commit TagA消息后，消费者可见
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                if("TagB".equals(tags)){
                    //回滚 TagB消息，消费者不能消费
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                //事务补偿 TagC消息，会触发broker进行回查
                return LocalTransactionState.UNKNOW;
            }
            
            /**
             * 事务补偿回查方法：执行本地事务方法消息Commit或Rollback发生超时，broker调该回查方法
             * @param msg
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("事务补偿回查方法 " + msg.getTags());
                //回查方法commit后，消费者就可以消费了
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        
        //启动消息生产者
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC"};
        for (int i = 0; i < 3; i++) {
            Message msg = new Message("test_topic", tags[i], "KEY" + i,
                    ("这是个GG公司 " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            String arg = "userId" + i;
            //发送事务消息：可以指定参数，传入本地事务方法
            SendResult sendResult = producer.sendMessageInTransaction(msg, arg);
            System.out.println(sendResult);
        }
        
        //不能立即关闭
        //producer.shutdown();
    }
    
    public static void main(String[] args) throws Exception {
        //发送同步消息
        syncProducer();
        
        //发送顺序消息
        //orderMsgProducer();
        
        //发送延迟消息
        //delayMsgProducer();
        
        //发送批量消息
        //batchMsgProducer();
        
        //发送设置用户属性的消息
        //sendUserPropertyMsg();
    
        //事务消息
        //sendTransactionMsg();   
    }
}
