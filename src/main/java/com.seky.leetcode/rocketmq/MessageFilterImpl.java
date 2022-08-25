package com.seky.leetcode.rocketmq;

import org.apache.rocketmq.common.filter.FilterContext;
import org.apache.rocketmq.common.filter.MessageFilter;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author: wf
 * @create: 2022/7/27 14:41
 * @description: 自定义类过滤机制
 */
public class MessageFilterImpl implements MessageFilter {
    @Override
    public boolean match(MessageExt msg, FilterContext context) {
        String tags = msg.getTags();
        String userId = msg.getUserProperty("userId");
        if ("TagA".equals(tags) && userId != null) {
            int id = Integer.parseInt(userId);
            if (((id % 3) == 0) && (id < 20)) {
                return true;
            }
        }
        return false;
    }
}

