package com.yniot.lms.config;

import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.service.WeChatService;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: lane
 * @Date: 2018/11/26 18:43
 * @Description:
 * @Version 1.0.0
 */
@Configuration
public class WeChatConfigurator {
    @Autowired
    CacheDao cacheDao;
    @Autowired
    WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage;
    @Autowired
    WxMpService wxMpService;
    @Autowired
    WxMpMessageRouter wxMpMessageRouter;

    /**
     * @return me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage
     * @Author wanggl
     * @Description 自动装配
     * @Date 21:06 2018-11-21
     * @Param []
     **/
    @Bean
    public WxMpInMemoryConfigStorage initConfig() {
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        WeChatConfig weChatConfig = cacheDao.get(WeChatService.CONFIG_KEY,WeChatConfig.class);
        config.setAesKey(weChatConfig.getAesKey());
        config.setAppId(weChatConfig.getAppId());
        config.setToken(weChatConfig.getToken());
        config.setSecret(weChatConfig.getAppSecret());
        return config;
    }

    @Bean
    public WxMpService initWxMpService() {
        WxMpService wxMpService = new WxMpServiceHttpClientImpl();
        wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
        return wxMpService;
    }

    @Bean
    public WxMpMessageRouter initWxMpMessageRouter() {
        WxMpMessageHandler logHandler = new WeChatLogHandler();
        WxMpMessageHandler textHandler = new WeChatTextHandler();
        WxMpMessageHandler imageHandler = new WeChatImageHandler();
        WxMpMessageHandler oauth2handler = new WeChatOAuth2Handler();
        WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
        wxMpMessageRouter
                .rule().handler(logHandler).next()
                .rule().async(false).content("哈哈").handler(textHandler).end()
                .rule().async(false).content("图片").handler(imageHandler).end()
                .rule().async(false).content("oauth").handler(oauth2handler).end();
        return wxMpMessageRouter;
    }
}
