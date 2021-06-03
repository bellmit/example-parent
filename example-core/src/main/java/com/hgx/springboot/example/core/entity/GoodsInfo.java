/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: GoodsInfo
 * Author:   gengxin.hao
 * Date:     2021/2/24 10:59
 * Description: 商品信息表
 * History:
 */
package com.hgx.springboot.example.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈商品信息表〉
 *
 * @author gengxin.hao
 * @create 2021/2/24
 * @since 1.0.0
 */
@Data
public class GoodsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String goodsId;

    private Integer goodsCount;

    private Integer version;

    private String status;

    private String remark;

    public static final String GOODS_ID = "goodsId";

}
