/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: TransactionLogServiceImpl
 * Author:   gengxin.hao
 * Date:     2021/2/25 11:29
 * Description:
 * History:
 */
package com.hgx.springboot.example.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgx.springboot.example.core.entity.TransactionLog;
import com.hgx.springboot.example.core.mapper.TransactionLogMapper;
import com.hgx.springboot.example.core.service.ITransactionLogService;
import org.springframework.stereotype.Service;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/2/25
 * @since 1.0.0
 */
@Service
public class TransactionLogServiceImpl extends ServiceImpl<TransactionLogMapper, TransactionLog>
        implements ITransactionLogService {
}
