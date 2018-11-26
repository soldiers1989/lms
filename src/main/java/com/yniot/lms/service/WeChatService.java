package com.yniot.lms.service;

import com.yniot.lms.db.entity.WeChatConfig;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @program: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-21 14:20
 **/
public interface WeChatService {
    String CONFIG_KEY = "WeChatAccountConfig";
    String LOGIN_MSG_TEMPLATE_ID = "218FmLW3m6DOiFpfvQp3ev148Jy6yhahzk9cpIe0Fq4";
    String ORDER_FINISHED_TEMPLATE_ID = "08kJsLEl7oY4f-ReYf3gvcM8BB1-3uFAi4uDxwF5XwE";
    //08kJsLEl7oY4f-ReYf3gvcM8BB1-3uFAi4uDxwF5XwE  订单完成
    //218FmLW3m6DOiFpfvQp3ev148Jy6yhahzk9cpIe0Fq4  登陆提醒

    WeChatConfig getConfig();

    void sendLoginNotice(String openId, String host) throws WxErrorException;

    int updateConfig(WeChatConfig weChatConfig);

}
