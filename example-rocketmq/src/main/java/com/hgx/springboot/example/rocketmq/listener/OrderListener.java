/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: OrderListener
 * Author:   gengxin.hao
 * Date:     2021/2/25 13:22
 * Description:
 * History:
 */
package com.hgx.springboot.example.rocketmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.hgx.springboot.example.core.entity.OrderInfo;
import com.hgx.springboot.example.rocketmq.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/25
 * @since 1.0.0
 */
@Component
@Slf4j
public class OrderListener implements MessageListenerConcurrently {

    private GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("消费者监听到消息");
        try {
            boolean goodsFlag = list.stream().anyMatch(message -> {
                log.info("开始处理订单数据,扣减库存");
                OrderInfo orderInfo = JSONObject.parseObject(message.getBody(), OrderInfo.class);
                try {
                    goodsService.updateGoods(orderInfo);
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return true;
                }
            });
            if (!goodsFlag) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }catch (Exception e) {
            log.info("消费者处理消息发生异常:{}",e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
