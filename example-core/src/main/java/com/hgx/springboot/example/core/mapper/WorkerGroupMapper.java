/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: WorkerGroupMapper
 * Author:   gengxin.hao
 * Date:     2021/5/13 10:44
 * Description:
 * History:
 */
package com.hgx.springboot.example.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hgx.springboot.example.core.entity.WorkerGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/5/13
 * @since 1.0.0
 */
@Mapper
@Repository
public interface WorkerGroupMapper extends BaseMapper<WorkerGroup> {
}
