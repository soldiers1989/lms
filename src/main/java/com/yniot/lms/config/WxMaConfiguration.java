package com.yniot.lms.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.google.common.collect.Maps;
import com.yniot.lms.service.wechat.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfiguration {
    private WxMaProperties properties;

    private static Map<String, WxMaMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxMaService> maServices = Maps.newHashMap();

    public static Map<String, WxMaMessageRouter> getRouters() {
        return routers;
    }

    public static Map<String, WxMaService> getMaServices() {
        return maServices;
    }
    @Autowired
    public WxMaConfiguration(WxMaProperties properties) {
        this.properties = properties;
    }
    @Bean
    public Object services() {

        maServices = this.properties.getConfigs()
                .stream()
                .map(a -> {
                    WxMaInMemoryConfig config = new WxMaInMemoryConfig();
                    config.setAppid(a.getAppid());
                    config.setSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
                    config.setMsgDataFormat(a.getMsgDataFormat());

                    WxMaService service = new WxMaServiceImpl();
                    service.setWxMaConfig(config);
                    routers.put(a.getAppid(), this.newRouter(service));
                    return service;
                }).collect(Collectors.toMap(s -> s.getWxMaConfig().getAppid(), a -> a));

        return Boolean.TRUE;
    }

    private WxMaMessageRouter newRouter(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        router.rule().handler(new SmallAppLogHandler()).next()
                .rule().async(false).content("模板").handler(new SmallAppTemplateMsgHandler()).end()
                .rule().async(false).content("文本").handler(new SmallAppTextHandler()).end()
                .rule().async(false).content("图片").handler(new SmallAppPicHandler()).end()
                .rule().async(false).content("二维码").handler(new SmallAppQRCodeHandler()).end();
        return router;
    }

}
