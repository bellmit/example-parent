/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: ChannelListener
 * Author:   gengxin.hao
 * Date:     2021/2/19 14:01
 * Description: 通道的监听器
 * History:
 */
package com.hgx.springboot.example.rocketmq.listener;

import com.hgx.springboot.example.core.entity.ChannelConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 〈通道的监听器〉
 *
 * @author gengxin.hao
 * @create 2021/2/19
 * @since 1.0.0
 */
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}", topic = "first-topic")
@Slf4j
public class ChannelListener implements RocketMQListener<ChannelConfig> {

    @Override
    public void onMessage(ChannelConfig channelConfig) {
        log.info("接收到消息,开始消费：{}", channelConfig);
    }
}
