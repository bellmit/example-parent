/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: RocketMqController
 * Author:   gengxin.hao
 * Date:     2021/2/19 13:42
 * Description: rocketmq controller
 * History:
 */
package com.hgx.springboot.example.rocketmq.controller;

//import com.hgx.springboot.example.core.entity.ChannelConfig;
import com.hgx.springboot.example.core.entity.ChannelConfig;
import com.hgx.springboot.example.rocketmq.utils.RocketMqHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈rocketMq controller〉
 *
 * @author gengxin.hao
 * @create 2021/2/19
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = {"/rocketMq"})
public class RocketMqController {

    private RocketMqHelper rocketMqHelper;

    @Autowired
    public void setRocketMqHelper(RocketMqHelper rocketMqHelper) {
        this.rocketMqHelper = rocketMqHelper;
    }

    @RequestMapping(value = {"/send"})
    public String sendMessage(@RequestBody ChannelConfig channelConfig){
        rocketMqHelper.asyncSend("first-topic", MessageBuilder.withPayload(channelConfig).build());
        return "发送成功";
    }

    @RequestMapping(value = {"/consume"})
    public String consume(@RequestBody String amount){

        return "消费成功";
    }
}
