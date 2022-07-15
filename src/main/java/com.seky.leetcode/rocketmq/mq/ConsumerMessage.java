package com.seky.leetcode.rocketmq.mq;

import lombok.Getter;
import org.apache.rocketmq.common.message.MessageExt;

import javax.annotation.PostConstruct;

/**
 * @author: wf
 * @create: 2020/12/8 15:43
 * @description: 消费消息处理接口
 */
public abstract class ConsumerMessage {
    @Getter
    protected String topic;
    
    @Getter
    protected String groupName;
    
    /**
     * 初始化topic和groupName (需要验证  @PostConstruct生不生效)
     */
    @PostConstruct
    public abstract void initConsumerMsg();
    
    /**
     * 消费者-消息处理接口
     * @param paramMessageExt
     * @return 返回false会进入重试
     */
    public abstract boolean receive(MessageExt paramMessageExt);
}
