/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: Worder
 * Author:   gengxin.hao
 * Date:     2021/5/13 9:40
 * Description: 员工类
 * History:
 */
package com.hgx.springboot.example.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈员工类〉
 *
 * @author gengxin.hao
 * @create 2021/5/13
 * @since 1.0.0
 */
@Data
@TableName("worker")
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String groupId;

    private String workerName;

    private String phone;

    private String sex;

    private String version;

    private String status;

    private String remark;
}
