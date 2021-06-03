/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: StaffShift
 * Author:   gengxin.hao
 * Date:     2021/5/13 9:47
 * Description: 员工排班表
 * History:
 */
package com.hgx.springboot.example.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈员工排班表〉
 *
 * @author gengxin.hao
 * @create 2021/5/13
 * @since 1.0.0
 */
@Data
@TableName("staff_shift")
public class StaffShift implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String workerIds;

    private String dayOfWeek;

    private String shiftIndex;

    private String version;

    private String status;

    private String remark;

}
