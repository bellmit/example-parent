/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: OrderParam
 * Author:   gengxin.hao
 * Date:     2021/2/23 16:28
 * Description:
 * History:
 */
package com.hgx.springboot.example.web.param;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/23
 * @since 1.0.0
 */
@Data
public class OrderParam {

    private String userId;

    private String goodsId;

    private String orderId;

    private LocalDateTime transTime;
}
