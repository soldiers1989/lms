package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseWxController;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.service.WeChatService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author wanggl
 * @Description //TODO
 * @Date 15:38 2018-11-21
 **/
@RestController
@RequestMapping(value = WeChatAPIController.WECHAT_PATH, produces = "text/plain;charset=UTF-8")
public class WeChatAPIController extends BaseWxController {
    public static final String WECHAT_PATH = "/WeChat";
    private static String TIMESTAMP_KEY = "timestamp";
    private static String SIGNATURE_KEY = "signature";
    private static String NONCE_KEY = "nonce";
    private static String ECHOSTR_KEY = "echostr";
    private static String ENCRYPT_TYPE_KEY = "encrypt_type";
    private static String MSG_SIGNATURE_KEY = "msg_signature";
    private static String RAW_KEY = "raw";
    private static String AES_KEY = "aes";
    @Autowired
    WeChatService weChatService;


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
        String timestamp = request.getParameter(TIMESTAMP_KEY);

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
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpInMemoryConfigStorage, timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            if (outMessage == null) {
                response.getWriter().write("outMessage == null");
                return;
            }
            response.getWriter().write(outMessage.toEncryptedXml(wxMpInMemoryConfigStorage));
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


    //6.获取用户列表
    @RequestMapping("/user/select")
    public String getAllUser(@RequestParam(name = "nextOpenId", required = false, defaultValue = "") String nextOpenId) throws WxErrorException {
        //oaend0YgraAE8JpUNSt4YN4tvZEk
        return super.getSuccessResult(this.wxMpService.getUserService().userList(nextOpenId));
    }

    @RequestMapping("/message/template/select")
    public String selectMessageTemplate(@RequestParam(name = "nextOpenId", required = false, defaultValue = "") String nextOpenId) throws WxErrorException {
        return super.getSuccessResult(this.wxMpService.getTemplateMsgService().getAllPrivateTemplate());
    }

    @RequestMapping("/message/template/create")
    public String createMessageTemplate(@RequestParam(name = "messageTemplate") String messageTemplate) throws WxErrorException {
        return super.getSuccessResult(this.wxMpService.getTemplateMsgService().addTemplate(messageTemplate));
    }

    @RequestMapping("/message/template/delete")
    public String deleteMessageTemplate(@RequestParam(name = "templateId") String templateId) throws WxErrorException {
        return super.getSuccessResult(this.wxMpService.getTemplateMsgService().delPrivateTemplate(templateId));
    }

    @RequestMapping("/message/send")
    public String sendMessage(@RequestBody WxMpTemplateMessage temp) throws WxErrorException {
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder().build();
        return super.getSuccessResult(this.wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage));
    }


    public static final String CALLBACK_PATH = "/OAuth2/callback";

    @RequestMapping(WeChatAPIController.CALLBACK_PATH)
    public String auth2Callback(@RequestParam(name = "code") String code) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = this.wxMpService.oauth2getAccessToken(code);
        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
//        wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(wxMpOAuth2AccessToken.getRefreshToken());
//        boolean valid = wxMpService.oauth2validateAccessToken(wxMpOAuth2AccessToken);
        return super.getSuccessResult(wxMpUser.getOpenId());
    }


    @RequestMapping("/auth2")
    public String auth2() throws WxErrorException {
        String callbackUrl = weChatService.getConfig().getCallbackDomain() + WeChatAPIController.WECHAT_PATH + WeChatAPIController.CALLBACK_PATH;
        return super.getSuccessResult(this.wxMpService.oauth2buildAuthorizationUrl(callbackUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null));
    }


    //    @Value("${wechat.auth.path}")
    private String authFilePath = "/usr/local/wechat";

    /**
     * @Author wanggl
     * @Description 获取授权文件
     * @Date 10:41 2018-11-22
     **/
    @RequestMapping(value = {"/{fileName:.*}"})//":.*"禁止忽略文件后缀名
    public ResponseEntity<byte[]> authFilePath(@PathVariable(value = "fileName") String fileName) throws IOException {
        return super.getFile(this.authFilePath,fileName);
    }


//    @RequestMapping("/testMessage")
//    public String testMessage() throws WxErrorException {
//        //    {{first.DATA}}
//        //    登陆时间：{{keyword1.DATA}}
//        //    登陆Ip：{{keyword2.DATA}}
//        //    {{remark.DATA}}
//        return super.getSuccessResult(wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage));
//    }

    //218FmLW3m6DOiFpfvQp3ev148Jy6yhahzk9cpIe0Fq4
    //    亲爱的姓名，您已经成功登陆某某管理系统
    //    登陆时间：2015年5月22日9:04:56
    //    登陆Ip：183.185.98.240
    //    如果不是您本人操作请联系系统管理员。


}
