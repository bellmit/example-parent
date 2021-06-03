/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: StaffShiftTask
 * Author:   gengxin.hao
 * Date:     2021/5/19 15:50
 * Description:
 * History:
 */
package com.hgx.springboot.example.web.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/5/19
 * @since 1.0.0
 */
@Component
@Slf4j
public class StaffShiftTask {

    @Scheduled(cron = "0 * * * * ?")
    public void shift() {
        log.info("begin shift");
    }
}
