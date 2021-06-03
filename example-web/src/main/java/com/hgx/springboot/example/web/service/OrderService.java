/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: OrderService
 * Author:   gengxin.hao
 * Date:     2021/2/24 15:12
 * Description:
 * History:
 */
package com.hgx.springboot.example.web.service;

import com.alibaba.fastjson.JSON;
import com.hgx.springboot.example.core.entity.OrderInfo;
import com.hgx.springboot.example.core.entity.TransactionLog;
import com.hgx.springboot.example.core.service.IOrderInfoService;
import com.hgx.springboot.example.core.service.ITransactionLogService;
import com.hgx.springboot.example.web.param.OrderParam;
import com.hgx.springboot.example.web.rocketmq.TransactionProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/24
 * @since 1.0.0
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private TransactionProducer producer;

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private ITransactionLogService transactionLogService;


    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderInfo orderInfo,String transactionId) throws Exception {
        //1.创建订单
        orderInfoService.save(orderInfo);
        if ("888".equals(orderInfo.getUserId())) {
            throw new Exception("错误用户");
        }
        //2.写入事务日志
        TransactionLog logInfo = new TransactionLog();
        logInfo.setId(transactionId);
        logInfo.setBusiness("order");
        logInfo.setForeignKey(String.valueOf(orderInfo.getOrderId()));
        transactionLogService.save(logInfo);
        log.info("订单创建完成:{}",orderInfo);
    }

    /**
     * @Description  发送扣减库存的half消息
     * @author gengxin.hao
     * @CreateDate 2021/2/25 13:13
     * @param
     * @return
     * @exception
     * @Version 1.0
     */
    public void createOrder(OrderParam param) throws MQClientException {
        param.setOrderId(UUID.randomUUID().toString());
        param.setTransTime(LocalDateTime.now());
        producer.send(JSON.toJSONString(param),"order");
    }
}
