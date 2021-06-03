/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: GoodsService
 * Author:   gengxin.hao
 * Date:     2021/2/25 13:23
 * Description:
 * History:
 */
package com.hgx.springboot.example.rocketmq.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hgx.springboot.example.core.entity.GoodsInfo;
import com.hgx.springboot.example.core.entity.OrderInfo;
import com.hgx.springboot.example.core.service.IGoodsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/25
 * @since 1.0.0
 */
@Service
@Slf4j
public class GoodsService {

    private IGoodsInfoService goodsInfoService;

    @Autowired
    public void setGoodsInfoService(IGoodsInfoService goodsInfoService) {
        this.goodsInfoService = goodsInfoService;
    }

    public void updateGoods(OrderInfo orderInfo) throws Exception {
        log.info("orderInfo:{}",orderInfo);
        String goodsId = orderInfo.getGoodsId();
        if (StringUtils.isBlank(goodsId)){
            log.info("goodsId is null");
            return;
        }
        QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
        wrapper.eq(GoodsInfo.GOODS_ID,goodsId);
        wrapper.last("limit 1");
        GoodsInfo goodsInfo = goodsInfoService.getOne(wrapper);
        if (goodsInfo == null) {
            log.info("goodsInfo is null");
            return;
        }
        if (goodsInfo.getGoodsCount() - 1 < 0){
            log.info("goodsInfo id is:{},count < 0",goodsId);
            throw new Exception("库存不足");
        }
        goodsInfo.setGoodsCount(goodsInfo.getGoodsCount() - 1);
        goodsInfoService.updateById(goodsInfo);
        log.info("库存扣减成功");
    }
}
