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
    String ORDER_GENERATED = "kWG6UDcUDlhf0OJrJSXoE_eTjzWCUXKbeuzDoXEiiQo";
    String PAY_NOTICE = "WnYc9LsUSXry-E5qHxZHuoDMAfep2JXAyTTEV400iMI";
    String PAID_NOTICE = "OPENTM400231951";
    String CLEANED = "MB_2haBvgqVoT-z2imY2AmI4Tb9BTz4ik1Swl7Jyvp4";
    String SEND = "OPENTM408128361";
    String ORDER_FINISHED_TEMPLATE_ID = "08kJsLEl7oY4f-ReYf3gvcM8BB1-3uFAi4uDxwF5XwE";
    String KEYWORD_KEY = "keyword";

    WeChatConfig getConfig();

    void sendLoginNotice(String openId, String host) throws WxErrorException;

    void sendOrderGenerateNotice(String orderNo) throws WxErrorException;

    void sendMailManTookNotice(String orderNo) throws WxErrorException;

    void sendUserTookNotice(String orderNo) throws WxErrorException;

    void sendMailManPutNotice(String orderNo) throws WxErrorException;

    void sendUserPutNotice(String orderNo) throws WxErrorException;

    void sendCleanedNotice(String orderNo) throws WxErrorException;

    void sendPaidSuccessNotice(String orderNo) throws WxErrorException;

    void sendPaidFailedNotice(String orderNo) throws WxErrorException;

    void sendNeedPayNotice(String orderNo) throws WxErrorException;

    void sendOrderCompleteNotice(String orderNo) throws WxErrorException;

    int updateConfig(WeChatConfig weChatConfig);

}
