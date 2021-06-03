/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: TransactionProducer
 * Author:   gengxin.hao
 * Date:     2021/2/25 11:01
 * Description:
 * History:
 */
package com.hgx.springboot.example.web.rocketmq;

import com.hgx.springboot.example.web.listener.OrderTransactionListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/25
 * @since 1.0.0
 */
@Component
@Slf4j
public class TransactionProducer {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    private String producerGroup = "order_trans_group";
    private TransactionMQProducer producer;

    private OrderTransactionListener orderTransactionListener;

    @Autowired
    public void setOrderTransactionListener(OrderTransactionListener orderTransactionListener) {
        this.orderTransactionListener = orderTransactionListener;
    }

    ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,
            60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(50));

    @PostConstruct
    public void init() {
        producer = new TransactionMQProducer(producerGroup);
        producer.setNamesrvAddr(nameServer);
        producer.setSendMsgTimeout(Integer.MAX_VALUE);
        producer.setExecutorService(executor);
        producer.setTransactionListener(orderTransactionListener);
        this.start();
    }

    private void start() {
        try {
            this.producer.start();
        }catch (Exception e) {
            log.info("RocketMq start exception:{}",e);
        }
    }

    //事务发送消息
    public TransactionSendResult send (String data,String topic) throws MQClientException {
        Message message = new Message(topic, data.getBytes());
        return this.producer.sendMessageInTransaction(message,null);
    }
}
