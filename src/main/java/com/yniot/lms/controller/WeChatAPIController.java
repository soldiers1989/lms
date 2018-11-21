package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.service.WeChatService;
import com.yniot.lms.service.wechat.WeChatImageHandler;
import com.yniot.lms.service.wechat.WeChatLogHandler;
import com.yniot.lms.service.wechat.WeChatOAuth2Handler;
import com.yniot.lms.service.wechat.WeChatTextHandler;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceHttpClientImpl;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author wanggl
 * @Description //TODO
 * @Date 15:38 2018-11-21
 **/
@RestController
@RequestMapping(value = "/WeChat", produces = "text/plain;charset=UTF-8")
public class WeChatAPIController extends BaseController {
    private static String TIMESTAME_KEY = "timestamp";
    private static String SIGNATURE_KEY = "signature";
    private static String NONCE_KEY = "nonce";
    private static String ECHOSTR_KEY = "echostr";
    private static String ENCRYPT_TYPE_KEY = "encrypt_type";
    private static String MSG_SIGNATURE_KEY = "msg_signature";
    private static String RAW_KEY = "raw";
    private static String AES_KEY = "aes";
    @Autowired
    WeChatService weChatService;
    @Autowired
    WxMpInMemoryConfigStorage config;
    @Autowired
    WxMpService wxMpService;
    @Autowired
    WxMpMessageRouter wxMpMessageRouter;

    /**
     * @Author wanggl
     * @Description 自动装配
     * @Date 21:06 2018-11-21
     * @Param []
     * @return me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage
     **/
    @Bean
    public WxMpInMemoryConfigStorage initConfig() {
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        WeChatConfig weChatConfig = weChatService.getConfig();
        config.setAesKey(weChatConfig.getAesKey());
        config.setAppId(weChatConfig.getAppId());
        config.setToken(weChatConfig.getToken());
        config.setSecret(weChatConfig.getAppSecret());
        return config;
    }

    @Bean
    public WxMpService initWxMpService() {
        WxMpService wxMpService = new WxMpServiceHttpClientImpl();
        wxMpService.setWxMpConfigStorage(config);
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

    /**
     * @return void
     * @Author wanggl
     * @Description //TODO 微信接口
     * @Date 15:13 2018-11-21
     * @Param [request, response]
     **/
    @RequestMapping("/api")
    public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter(SIGNATURE_KEY);
        String timestamp = request.getParameter(TIMESTAME_KEY);
        String nonce = request.getParameter(NONCE_KEY);
        String echostr = request.getParameter(ECHOSTR_KEY);
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            response.getWriter().write("illegal request!");
            return;
        }
        if (StringUtils.isNotEmpty(echostr)) {
            response.getWriter().write(echostr);
            return;
        }
        String encryptType = request.getParameter(ENCRYPT_TYPE_KEY);
        if (StringUtils.isNotEmpty(encryptType)) {
            encryptType = RAW_KEY;
        }
        if (RAW_KEY.equals(encryptType)) {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            if (outMessage == null) {
                response.getWriter().write("outMessage == null");
                return;
            }
            response.getWriter().write(outMessage.toXml());
            return;
        } else if (AES_KEY.equals(encryptType)) {
            String msgSignature = request.getParameter(MSG_SIGNATURE_KEY);
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), config, timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            if (outMessage == null) {
                response.getWriter().write("outMessage == null");
                return;
            }
            response.getWriter().write(outMessage.toEncryptedXml(config));
            return;
        }
    }


    //1.微信菜单修改
    @RequestMapping("/menu/create")
    public String createWeChatMenu(@RequestParam(name = "menu") String jsonMenu) throws WxErrorException {
        WxMenu menu = WxMenu.fromJson(jsonMenu);
        return super.getSuccessResult(wxMpService.getMenuService().menuCreate(menu));
    }


    @RequestMapping("/menu/update")
    public String updateWeChatMenu() throws WxErrorException {
        String a = "{\n" +
                "  \"button\": [\n" +
                "    {\n" +
                "      \"type\": \"view\",\n" +
                "      \"name\": \"我的账户\",\n" +
                "      \"url\": \"http://www.soso.com/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"申请加盟\",\n" +
                "      \"sub_button\": [\n" +
                "        {\n" +
                "          \"type\": \"view\",\n" +
                "          \"name\": \"成为店家\",\n" +
                "          \"url\": \"http://www.soso.com/\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"view\",\n" +
                "          \"name\": \"成为配送员\",\n" +
                "          \"url\": \"http://www.soso.com/\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return super.getSuccessResult(this.wxMpService.getMenuService().menuCreate(a));
    }


    //2.微信菜单删除
    @RequestMapping("/menu/delete")
    public String deleteWeChatMenu() throws WxErrorException {
        wxMpService.getMenuService().menuDelete();
        return super.getSuccessResult(1);
    }

    //3.获取微信菜单
    @RequestMapping("/menu/select")
    public String selectWeChatMenu() throws WxErrorException {
        WxMpMenu wxMenu = wxMpService.getMenuService().menuGet();
        return super.getSuccessResult(wxMenu.getMenu().getButtons());
    }


    //4.微信公众号配置修改
    @RequestMapping("/config/update")
    public String changeWeChatMenu(@RequestBody WeChatConfig weChatConfig) {
        return super.getSuccessResult(weChatService.updateConfig(weChatConfig));
    }


    //5.获取微信公众号配置
    @RequestMapping("/config/select")
    public String selectWeChatConfig() {
        return super.getSuccessResult(weChatService.getConfig());
    }

}
