package com.yniot.lms.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.yniot.lms.config.WxMaConfiguration;
import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.UserService;
import com.yniot.lms.utils.MessageUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author wanggl
 * @Description //TODO
 * @Date 15:38 2018-11-21
 **/
@RestController
@RequestMapping(value = SmallAppAPIController.WECHAT_APP_PATH + "/{appId}", produces = "text/plain;charset=UTF-8")
public class SmallAppAPIController extends BaseController {
    public static final String WECHAT_APP_PATH = "/SmallAppApi";

    @Autowired
    UserService userService;
    @Autowired
    CacheDao cacheDao;
    @Autowired
    MessageUtil messageUtil;

    /**
     * 登陆接口
     */
    @RequestMapping("/login")
    public String login(@PathVariable String appId, String code) throws ClientException {
        if (StringUtils.isBlank(code)) {
            return getErrorMsg("code为空!");
        }

        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appId);
        if (wxService == null) {
            return getErrorMsg("appId不存在!");
        }
        try {
            WxMaJscode2SessionResult sessionWx = wxService.getUserService().getSessionInfo(code);
            String openId = sessionWx.getOpenid();
            String sessionKey = sessionWx.getSessionKey();
            User user = userService.selectByAppIdNOpenId(appId, openId);
            if (user == null) {
                //创建用户
            }
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());//这里的密码经过MD5
            subject.login(usernamePasswordToken);
            Session sessionShiro = subject.getSession();
            String token = sessionShiro.getId().toString();
            cacheDao.set(token, sessionKey, 45, TimeUnit.MINUTES);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("openId", openId);
            jsonObject.put("userInfo", user);
            jsonObject.put("userId", user.getId());
            return getSuccessResult(jsonObject);
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
                       String token,
                       String signature,
                       String rawData,
                       String encryptedData,
                       String iv)  {
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appId);
        if (wxService == null) {
            return getErrorMsg(String.format("未找到对应appId=[%d]的配置！", appId));
        }
        String sessionKey = cacheDao.get(token);
        if (StringUtils.isEmpty(sessionKey)) {
            return getErrorMsg("已超时!");
        }
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return getErrorMsg("用户信息校验失败!");
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        logger.info("openId:" + userInfo.getOpenId());
        logger.info("unionId:" + userInfo.getUnionId());
        return getSuccessResult(userInfo.getUnionId());
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @RequestMapping("/phone")
    public String phone(@PathVariable String appId, String token, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appId);
        if (wxService == null) {
            return getErrorMsg(String.format("未找到对应appId=[%d]的配置！", appId));
        }
        // 用户信息校验
        String sessionKey = cacheDao.get(token);
        if (StringUtils.isEmpty(sessionKey)) {
            return getErrorMsg("已超时!");
        }
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "用户信息校验错误";
        }
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        return getJsonStr(phoneNoInfo);
    }


}
