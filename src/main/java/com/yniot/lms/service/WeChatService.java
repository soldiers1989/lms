package com.yniot.lms.service;

import com.yniot.lms.db.entity.WeChatConfig;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @program: lms
 * @description: ${description}
 * @author: wanggl
 * @create: 2018-11-21 14:20
 **/
public interface WeChatService {

    WeChatConfig getConfig();

    int updateConfig(WeChatConfig weChatConfig);

    int sendMessage() throws WxErrorException;
}
