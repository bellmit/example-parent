/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: OrderTransactionListener
 * Author:   gengxin.hao
 * Date:     2021/2/25 11:03
 * Description:
 * History:
 */
package com.hgx.springboot.example.web.listener;

import com.alibaba.fastjson.JSONObject;
import com.hgx.springboot.example.core.entity.OrderInfo;
import com.hgx.springboot.example.core.entity.TransactionLog;
import com.hgx.springboot.example.core.service.ITransactionLogService;
import com.hgx.springboot.example.web.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/25
 * @since 1.0.0
 */
@Component
@Slf4j
public class OrderTransactionListener implements TransactionListener {

    private OrderService orderService;

    private ITransactionLogService transactionLogService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setTransactionLogService(ITransactionLogService transactionLogService) {
        this.transactionLogService = transactionLogService;
    }

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("开始执行本地事务");
        LocalTransactionState state;
        try {
            String body = new String(message.getBody());
            OrderInfo orderInfo = JSONObject.parseObject(body, OrderInfo.class);
            orderService.createOrder(orderInfo,message.getTransactionId());
            state = LocalTransactionState.COMMIT_MESSAGE;
            log.info("本地事务已提交:{}",message.getTransactionId());
        }catch (Exception e) {
            log.info("执行本地事务失败:{}",e);
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return state;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        log.info("开始回查本地事务状态：{}",messageExt.getTransactionId());
        LocalTransactionState state;
        String transactionId = messageExt.getTransactionId();
        TransactionLog transactionLog = transactionLogService.getById(transactionId);
        if (transactionLog != null) {
            state = LocalTransactionState.COMMIT_MESSAGE;
        }else {
            state = LocalTransactionState.UNKNOW;
        }
        log.info("结束本地事务状态查询：{}",state);
        return state;
    }
}
