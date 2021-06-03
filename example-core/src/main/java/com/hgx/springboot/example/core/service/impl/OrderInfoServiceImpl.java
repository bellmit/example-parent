/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: OrderInfoServiceImpl
 * Author:   gengxin.hao
 * Date:     2021/2/24 11:13
 * Description:
 * History:
 */
package com.hgx.springboot.example.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgx.springboot.example.core.entity.OrderInfo;
import com.hgx.springboot.example.core.mapper.OrderInfoMapper;
import com.hgx.springboot.example.core.service.IOrderInfoService;
import org.springframework.stereotype.Service;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/24
 * @since 1.0.0
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {
}
