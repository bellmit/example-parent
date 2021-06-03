/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: StaffShiftServiceImpl
 * Author:   gengxin.hao
 * Date:     2021/5/13 10:06
 * Description:
 * History:
 */
package com.hgx.springboot.example.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgx.springboot.example.core.entity.StaffShift;
import com.hgx.springboot.example.core.mapper.StaffShiftMapper;
import com.hgx.springboot.example.core.service.IStaffShiftService;
import org.springframework.stereotype.Service;


/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/5/13
 * @since 1.0.0
 */
@Service
public class StaffShiftServiceImpl extends ServiceImpl<StaffShiftMapper, StaffShift> implements IStaffShiftService {
}
