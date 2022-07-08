package com.seky.leetcode.rocketmq.mq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: wf
 * @create: 2020/12/8 15:42
 * @description: 消费者
 */
public class ConsumerProxy {
    private static final Logger log = LoggerFactory.getLogger(ConsumerProxy.class);

    private Map<String, ConsumerMessage> topics;
    private String namesrv;
    private DefaultMQPushConsumer consumer;
    private String groupName;
    private String unitName;
    private static final int DEFAULT_RETRY = 5;
    private static final int CORE_THREAD_SIZE = 5;
    private static final int MAX_THREAD_SIZE = 20;
    private static final int PULL_THRESHOLD_QUEUE = 50;

    /**
     * 构造器(默认集群模式)
     * @param topics
     * @param namesrv
     * @param groupName
     */
    public ConsumerProxy(Map<String, ConsumerMessage> topics, String namesrv, String groupName) {
        this.topics = topics;
        this.namesrv = namesrv;
        this.groupName = groupName;
        initConsumer(MAX_THREAD_SIZE, DEFAULT_RETRY, null, 0L, CORE_THREAD_SIZE);
    }

    /**
     * 构造器(默认集群模式)
     * @param topicList
     * @param namesrv
     * @param groupName
     */
    public ConsumerProxy(List<ConsumerMessage> topicList, String namesrv, String groupName) {
        this.topics = topicList.stream().collect(Collectors.toMap(ConsumerMessage::getTopic, f -> f));
        this.namesrv = namesrv;
        this.groupName = groupName;
        initConsumer(MAX_THREAD_SIZE, DEFAULT_RETRY, null, 0L, CORE_THREAD_SIZE);
    }

    /**
     * 构造器(指定集群模式)
     * @param topics
     * @param messageModel
     * @param namesrv
     * @param groupName
     */
    public ConsumerProxy(Map<String, ConsumerMessage> topics, MessageModel messageModel, String namesrv, String groupName) {
        this.topics = topics;
        this.namesrv = namesrv;
        this.groupName = groupName;
        initConsumer(MAX_THREAD_SIZE, DEFAULT_RETRY, messageModel, 0L, CORE_THREAD_SIZE);
    }
    
    /**
     * 构造器 TODO 根据环境选择 nameserver, 使用不需要配置IP
     * @param topics
     * @param namesrv
     * @param groupName
     * @param env
     */
    private ConsumerProxy(Map<String, ConsumerMessage> topics, String namesrv, String groupName, String env) {
    }

    /**
     * 消费者初始化
     * @param maxThreadSize
     * @param retryTime
     * @param messageModel
     * @param consumeTimeout
     * @param coreThreadSize
     */
    private void initConsumer(int maxThreadSize, int retryTime, MessageModel messageModel, long consumeTimeout, int coreThreadSize) {
        if (this.topics == null) {
            log.error("topics is null");
            return;
        }
        if (StringUtils.isEmpty(this.namesrv)) {
            log.error("namesrv is null");
            return;
        }
        initConsumerCommon(maxThreadSize, retryTime, messageModel, consumeTimeout, coreThreadSize);
        try {
            Iterator<String> iterator = this.topics.keySet().iterator();
            while (iterator.hasNext()) {
                String topic = iterator.next();
                String tags = "*";
                this.consumer.subscribe(topic, tags);
            }
            this.consumer.registerMessageListener(genMessageListener());
            this.consumer.start();
        } catch (Exception e) {
            log.error("init the consumer error", e);
            throw new InitConsumerException(e.getMessage());
        }
    }

    /**
     * 初始化消费者参数
     *
     * @param maxThreadSize
     * @param retryTime
     * @param messageModel
     * @param consumeTimeout
     * @param coreThreadSize
     */
    private void initConsumerCommon(int maxThreadSize, int retryTime, MessageModel messageModel, long consumeTimeout, int coreThreadSize) {
        this.consumer = new DefaultMQPushConsumer(this.groupName);
        this.consumer.setNamesrvAddr(this.namesrv);
        this.consumer.setConsumeThreadMax(maxThreadSize);
        this.consumer.setConsumeThreadMin(coreThreadSize);
        this.consumer.setMaxReconsumeTimes(retryTime);
        this.consumer.setPullThresholdForQueue(PULL_THRESHOLD_QUEUE);
        if (messageModel != null) {
            this.consumer.setMessageModel(messageModel);
        }
        if (consumeTimeout > 0L) {
            this.consumer.setConsumeTimeout(consumeTimeout);
        }
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        if (StringUtils.isNotBlank(this.unitName))
            this.consumer.setUnitName(this.unitName);
    }

    /**
     * topic监听器
     * @return
     */
    private MessageListenerConcurrently genMessageListener() {
        return (msgs, context) -> {
            for (int i = 0; i < msgs.size(); i++) {
                ConsumerMessage consumerMessage = ConsumerProxy.this.topics.get(msgs.get(i).getTopic());
                if (consumerMessage == null){
                    ConsumerProxy.log.error("no topic={} for service", msgs.get(i).getTopic());
                }
                try {
                    if (!consumerMessage.receive(msgs.get(i)))
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                } catch (Exception e) {
                    ConsumerProxy.log.error("consumer message error", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        };
    }
}
