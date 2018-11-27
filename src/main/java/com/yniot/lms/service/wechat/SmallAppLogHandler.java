package com.yniot.lms.service.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;

import java.util.Map;

/**
 * @Auther: lane
 * @Date: 2018/11/27 16:38
 * @Description:
 * @Version 1.0.0
 */
public class SmallAppLogHandler implements WxMaMessageHandler {
    @Override
    public void handle(WxMaMessage wxMaMessage, Map<String, Object> map, WxMaService wxMaService, WxSessionManager wxSessionManager) throws WxErrorException {

//        router.rule().handler(logHandler).next()
//                .rule().async(false).content("模板").handler(templateMsgHandler).end()
//                .rule().async(false).content("文本").handler(textHandler).end()
//                .rule().async(false).content("图片").handler(picHandler).end()
//                .rule().async(false).content("二维码").handler(qrcodeHandler).end();

    }
}
