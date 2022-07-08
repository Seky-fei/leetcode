package com.igetcool.base.commons.mq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: wf
 * @create: 2021/4/19 14:24
 * @description:
 */
public class ConsumerProxyService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerProxyService.class);
    private Map<String, ConsumerMessage> topics;
    private String namesrv;
    private static final int DEFAULT_RETRY = 5;
    
    /**
     * @param topicList
     * @param namesrv
     */
    public ConsumerProxyService(List<ConsumerMessage> topicList, String namesrv) {
        this.topics = topicList.stream().collect(Collectors.toMap(ConsumerMessage::getTopic, f -> f));
        this.namesrv = namesrv;
        this.initConsumer(DEFAULT_RETRY, (MessageModel) null, 0L);
    }
    
    /**
     * 消费者初始化
     *
     * @param retryTime
     * @param messageModel
     * @param consumeTimeout
     */
    private void initConsumer(int retryTime, MessageModel messageModel, long consumeTimeout) {
        if (this.topics == null) {
            logger.error("topics is null");
            return;
        }
        if (StringUtils.isEmpty(this.namesrv)) {
            logger.error("namesrv is null");
            return;
        }
        try {
            Iterator<ConsumerMessage> iterator = this.topics.values().iterator();
            while (iterator.hasNext()) {
                DefaultMQPushConsumer consumer = initConsumerCommon(iterator.next(), retryTime, messageModel, consumeTimeout);
                consumer.start();
            }
        } catch (Exception e) {
            logger.error("init the consumer error", e);
            throw new InitConsumerException(e.getMessage());
        }
    }
    
    /**
     * 初始化消费者
     *
     * @param retryTime
     * @param messageModel
     * @param consumeTimeout
     * @return
     */
    private DefaultMQPushConsumer initConsumerCommon(ConsumerMessage consumerMsg, int retryTime, MessageModel messageModel, long consumeTimeout) throws MQClientException {
        String tags = "*";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerMsg.getGroupName());
        consumer.setNamesrvAddr(this.namesrv);
        consumer.subscribe(consumerMsg.getTopic(), tags);
        consumer.setMaxReconsumeTimes(retryTime);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        if (messageModel != null) {
            consumer.setMessageModel(messageModel);
        }
        if (consumeTimeout > 0L) {
            consumer.setConsumeTimeout(consumeTimeout);
        }
        consumer.registerMessageListener(genMessageListener());
        return consumer;
    }
    
    /**
     * topic监听器注册
     *
     * @return
     */
    private MessageListenerConcurrently genMessageListener() {
        return (msgs, context) -> {
            for (int i = 0; i < msgs.size(); i++) {
                String topic = msgs.get(i).getTopic();
                ConsumerMessage consumerMsg = topics.get(topic);
                if (consumerMsg == null) {
                    logger.error("no topic = {} for service", topic);
                }
                try {
                    if (!consumerMsg.receive(msgs.get(i)))
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                } catch (Exception e) {
                    logger.error("consumer message error", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        };
    }
}
