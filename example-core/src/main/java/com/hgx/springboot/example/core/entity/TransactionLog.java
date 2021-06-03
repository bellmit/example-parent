/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: TransactionLog
 * Author:   gengxin.hao
 * Date:     2021/2/25 11:26
 * Description:
 * History:
 */
package com.hgx.springboot.example.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/25
 * @since 1.0.0
 */
@Data
@TableName("transaction_log")
public class TransactionLog {

    private static final long serialVersionUID = 1L;

    private String id;

    private String business;

    private String foreignKey;
}
