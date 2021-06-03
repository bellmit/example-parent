/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: OrderController
 * Author:   gengxin.hao
 * Date:     2021/2/23 16:24
 * Description: 订单接口
 * History:
 */
package com.hgx.springboot.example.web.controller;

import com.hgx.springboot.example.core.entity.OrderInfo;
import com.hgx.springboot.example.core.service.IOrderInfoService;
import com.hgx.springboot.example.web.param.OrderParam;
import com.hgx.springboot.example.web.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * 〈订单接口〉
 *
 * @author gengxin.hao
 * @create 2021/2/23
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/order")
@Slf4j
public class OrderController {

    private IOrderInfoService orderInfoService;

    @Autowired
    public void setOrderInfoService(IOrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = {"/consume"})
    public String consume(@RequestBody OrderParam param) {
        try {
            log.info("consume param:{}",param);
            orderService.createOrder(param);
            return "消费成功";
        }catch (Exception e) {
            log.info("consume exception:{}",e);
        }
        return "消费失败";
    }

    public void merchantIn() {
    }
}
