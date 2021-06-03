/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: ChannelConfig
 * Author:   gengxin.hao
 * Date:     2021/2/19 13:58
 * Description: 通道配置
 * History:
 */
package com.hgx.springboot.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈通道配置〉
 *
 * @author gengxin.hao
 * @create 2021/2/19
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelConfig {

    public String channelName;

    public Integer maxSize;


}
