package com.seky.leetcode.rocketmq.mq;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: wf
 * @create: 2022/7/8 14:15
 * @description:  rocketmq配置初始化(集成到springBoot)
 */
//@Configuration
//@RefreshScope
public class ConsumerMqConfig {
    
    //@Value("${rocketmq.nameserver.address}")
    private String nameSrvAddress;
    
    @Resource
    private List<ConsumerMessage> consumerList;
    
    /**
     * (集成多个Consumer时,继承ConsumerMessage即可)
     * @return
     */
    //@Bean
    public ConsumerProxyService consumerProxy() {
        return new ConsumerProxyService(consumerList, nameSrvAddress);
    }
}
