/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: StaffShiftController
 * Author:   gengxin.hao
 * Date:     2021/5/13 10:37
 * Description:
 * History:
 */
package com.hgx.springboot.example.web.controller;

import com.hgx.springboot.example.core.entity.Worker;
import com.hgx.springboot.example.core.entity.WorkerGroup;
import com.hgx.springboot.example.core.service.IStaffShiftService;
import com.hgx.springboot.example.web.dto.WorkerGroupDto;
import com.hgx.springboot.example.web.service.StaffShiftService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/5/13
 * @since 1.0.0
 */
@RestController
@Slf4j
public class StaffShiftController {

    private StaffShiftService staffShiftService;

    @Autowired
    public void setStaffShiftService(StaffShiftService staffShiftService) {
        this.staffShiftService = staffShiftService;
    }

    @RequestMapping(value = "/getAllGroup")
    public List<WorkerGroup> getAllGroup() {
        List<WorkerGroup> workerGroupList = staffShiftService.getAllGroup();
        workerGroupList.stream().forEach(workerGroup -> {
            log.info("workerGroup={}", workerGroup);
        });
        return workerGroupList;
    }

    @RequestMapping(value = "/getWorkerByGroupId")
    public List<Worker> getWorkerByGroupId(@RequestBody Worker worker) {
        if (StringUtils.isBlank(worker.getGroupId())) {
            log.info("param groupID is null");
            return null;
        }
        return staffShiftService.getWorkerByGroupId(worker);
    }
}
