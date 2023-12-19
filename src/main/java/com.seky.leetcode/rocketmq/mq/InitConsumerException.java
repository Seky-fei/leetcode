package com.seky.leetcode.rocketmq.mq;

/**
 * @author: wf
 * @create: 2020/12/8 16:09
 * @description:
 */
public class InitConsumerException extends RuntimeException {
    private static final long serialVersionUID = -3607969877330485879L;

    public InitConsumerException() {
    }

    public InitConsumerException(String message) {
        super(message);
    }
}
