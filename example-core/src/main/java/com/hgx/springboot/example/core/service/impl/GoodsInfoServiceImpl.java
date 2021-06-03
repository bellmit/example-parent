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
import com.hgx.springboot.example.core.entity.GoodsInfo;
import com.hgx.springboot.example.core.mapper.GoodsInfoMapper;
import com.hgx.springboot.example.core.service.IGoodsInfoService;
import org.springframework.stereotype.Service;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/24
 * @since 1.0.0
 */
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements IGoodsInfoService {
}
