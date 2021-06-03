/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: StaffShiftService
 * Author:   gengxin.hao
 * Date:     2021/5/13 11:02
 * Description:
 * History:
 */
package com.hgx.springboot.example.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hgx.springboot.example.core.entity.Worker;
import com.hgx.springboot.example.core.entity.WorkerGroup;
import com.hgx.springboot.example.core.mapper.WorkerGroupMapper;
import com.hgx.springboot.example.core.mapper.WorkerMapper;
import com.hgx.springboot.example.web.dto.WorkerGroupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/5/13
 * @since 1.0.0
 */
@Slf4j
@Service
public class StaffShiftService {

    private WorkerGroupMapper workerGroupMapper;

    private WorkerMapper workerMapper;

    @Autowired
    public void setWorkerGroupMapper(WorkerGroupMapper workerGroupMapper) {
        this.workerGroupMapper = workerGroupMapper;
    }

    @Autowired
    public void setWorkerMapper(WorkerMapper workerMapper) {
        this.workerMapper = workerMapper;
    }

    public List<WorkerGroup> getAllGroup() {
        QueryWrapper<WorkerGroup> query = new QueryWrapper();
        query.orderByAsc("groupId");
        List<WorkerGroup> workerGroups = workerGroupMapper.selectList(query);
        workerGroups.stream().forEach(workerGroup -> {
            String groupId = workerGroup.getGroupId();
            WorkerGroupDto groupDto = new WorkerGroupDto();
            BeanUtils.copyProperties(workerGroup, groupDto);
            QueryWrapper<Worker> queryWorker = new QueryWrapper<>();
            queryWorker.eq("groupId", groupId);
            List<Worker> workers = workerMapper.selectList(queryWorker);

        });
        return workerGroups;
    }

    public List<Worker> getWorkerByGroupId(Worker worker) {
        QueryWrapper<Worker> queryWorker = new QueryWrapper<>();
        queryWorker.eq("groupId", worker.getGroupId());
        return workerMapper.selectList(queryWorker);
    }
}
