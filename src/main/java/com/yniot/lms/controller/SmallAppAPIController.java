package com.yniot.lms.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.yniot.lms.config.WxMaConfiguration;
import com.yniot.lms.controller.commonController.BaseController;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wanggl
 * @Description //TODO
 * @Date 15:38 2018-11-21
 **/
@RestController
@RequestMapping(value = SmallAppAPIController.WECHAT_APP_PATH + "/{appId}", produces = "text/plain;charset=UTF-8")
public class SmallAppAPIController extends BaseController {
    public static final String WECHAT_APP_PATH = "/SmallAppApi";

    /**
     * 登陆接口
     */
    @RequestMapping("/login")
    public String login(@PathVariable String appId, @RequestParam(name = "code") String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appId);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%d]的配置，请核实！", appId));
        }
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return getSuccessResult(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @RequestMapping("/info")
    public String info(@PathVariable String appId,
                       @RequestParam(name = "sessionKey") String sessionKey,
                       @RequestParam(name = "signature") String signature,
                       @RequestParam(name = "rawData") String rawData,
                       @RequestParam(name = "encryptedData") String encryptedData,
                       @RequestParam(name = "iv") String iv  ) {

        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appId);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%d]的配置，请核实！", appId));
        }

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return getJsonStr(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @RequestMapping("/phone")
    public String phone(@PathVariable String appId, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appId);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%d]的配置，请核实！", appId));
        }

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return getJsonStr(phoneNoInfo);
    }


}
