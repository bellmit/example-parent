/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: OrderInfoMapper
 * Author:   gengxin.hao
 * Date:     2021/2/24 11:05
 * Description:
 * History:
 */
package com.hgx.springboot.example.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hgx.springboot.example.core.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/24
 * @since 1.0.0
 */
@Mapper
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
}
