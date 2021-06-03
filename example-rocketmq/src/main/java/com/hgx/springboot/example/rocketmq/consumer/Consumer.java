/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: Consumer
 * Author:   gengxin.hao
 * Date:     2021/2/25 13:50
 * Description:
 * History:
 */
package com.hgx.springboot.example.rocketmq.consumer;

import com.hgx.springboot.example.rocketmq.listener.OrderListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/25
 * @since 1.0.0
 */
@Component
public class Consumer {

    private String consumerGroup = "consumer-group";

    @Value("${rocketmq.name-server}")
    private String nameServer;

    DefaultMQPushConsumer consumer;

    private OrderListener orderListener;

    @Autowired
    public void setOrderListener(OrderListener orderListener) {
        this.orderListener = orderListener;
    }

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.subscribe("order","*");
        consumer.registerMessageListener(orderListener);
        consumer.setMaxReconsumeTimes(3);
        consumer.start();
    }


}
