/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: OrderInfo
 * Author:   gengxin.hao
 * Date:     2021/2/24 10:58
 * Description: 订单表
 * History:
 */
package com.hgx.springboot.example.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 〈订单表〉
 *
 * @author gengxin.hao
 * @create 2021/2/24
 * @since 1.0.0
 */
@Data
@TableName("order_info")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userId;

    private String goodsId;

    private String orderId;

    private BigDecimal transAmt;

    private LocalDateTime transTime;

    private Integer version;

    private String status;

    private String remark;

}
