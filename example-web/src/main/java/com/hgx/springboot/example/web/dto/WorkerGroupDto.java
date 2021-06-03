/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: GroupDto
 * Author:   gengxin.hao
 * Date:     2021/5/13 13:44
 * Description:
 * History:
 */
package com.hgx.springboot.example.web.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hgx.springboot.example.core.entity.Worker;
import lombok.Data;

import java.util.List;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/5/13
 * @since 1.0.0
 */
@Data
public class WorkerGroupDto {

    private Long id;

    private String groupId;

    private String groupName;

    private String version;

    private String status;

    private String remark;

    private List<Worker> workers;
}
