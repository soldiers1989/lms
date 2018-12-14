package com.yniot.lms.controller.commonController;

import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.service.WeChatService;
import com.yniot.lms.service.impl.WeChatServiceImpl;
import com.yniot.lms.service.wechat.WeChatImageHandler;
import com.yniot.lms.service.wechat.WeChatLogHandler;
import com.yniot.lms.service.wechat.WeChatOAuth2Handler;
import com.yniot.lms.service.wechat.WeChatTextHandler;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceHttpClientImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: lane
 * @Date: 2018-12-14 16:40
 * @Description:
 * @Version 1.0.0
 */
//@Component
public class BaseWxController extends BaseController {
    protected static WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = null;
    protected static WxMpService wxMpService = null;
    protected static WxMpMessageRouter wxMpMessageRouter = null;

    /**
     * @return me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage
     * @Author wanggl
     * @Description 自动装配
     * @Date 21:06 2018-11-21
     * @Param []
     **/
    public static void initConfig(WeChatService weChatService) {
        if (wxMpInMemoryConfigStorage == null) {
            wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
            WeChatConfig weChatConfig = weChatService.getConfig();
            wxMpInMemoryConfigStorage.setAesKey(weChatConfig.getAesKey());
            wxMpInMemoryConfigStorage.setAppId(weChatConfig.getAppId());
            wxMpInMemoryConfigStorage.setToken(weChatConfig.getToken());
            wxMpInMemoryConfigStorage.setSecret(weChatConfig.getAppSecret());
        }
//        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
//        config.setAppId("wx74b6b27d0799f9d7");
//        config.setSecret("2fb7e814d855d0857cc5c09df2335023");
//        config.setToken("lms_token");
//        config.setAesKey("XCaOiGUwuU48Zin8yjHDsk6V1THBbxBmO4CIViZ4nlr");
        if (wxMpService == null) {
            wxMpService = new WxMpServiceHttpClientImpl();
            wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
        }
        if (wxMpMessageRouter == null) {
            wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
            WxMpMessageHandler logHandler = new WeChatLogHandler();
            WxMpMessageHandler textHandler = new WeChatTextHandler();
            WxMpMessageHandler imageHandler = new WeChatImageHandler();
            WxMpMessageHandler oauth2handler = new WeChatOAuth2Handler();
            wxMpMessageRouter
                    .rule().handler(logHandler).next()
                    .rule().async(false).content("哈哈").handler(textHandler).end()
                    .rule().async(false).content("图片").handler(imageHandler).end()
                    .rule().async(false).content("oauth").handler(oauth2handler).end();
        }

    }
}
