package com.yniot.lms.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.yniot.lms.annotation.LowPerformance;
import com.yniot.lms.config.WxMaConfiguration;
import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.entity.RelUserApp;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.enums.ErrorMsgEnum;
import com.yniot.lms.enums.UserTypeEnum;
import com.yniot.lms.service.RelUserAppService;
import com.yniot.lms.service.UserService;
import com.yniot.lms.utils.CommonUtil;
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

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author wanggl
 * @Description //TODO
 * @Date 15:38 2018-11-21
 **/
@RestController
@RequestMapping(value = "{appId}/" + SmallAppAPIController.WECHAT_APP_PATH, produces = "text/plain;charset=UTF-8")
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
            WxMaJscode2SessionResult sessionWx = wxService.jsCode2SessionInfo(code);
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


    private static final String CODE_PREFIX = "code_";

    private boolean isCodeMatch(String code, String openId) {
        return CommonUtil.String.validStr(code, openId) && code.equals(cacheDao.get(CODE_PREFIX + openId));
    }

    private void sendCode(String openId, String phone) throws ClientException {
        String code = CommonUtil.String.RandomCheckCode(6);
        cacheDao.set(CODE_PREFIX + openId, code, 5, TimeUnit.MINUTES);
        messageUtil.sendRegisterCode(phone, code);
    }


//    @RequestMapping("/loginByCode")
//    public String loginByCode(@PathVariable String appId, String phone, String checkCode) {
//
//    }

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
                       String iv) {
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


    @Autowired
    RelUserAppService relUserAppService;


    @LowPerformance
    @RequestMapping("/register")
    public String register(@PathVariable String appId, String openId, String phone, String password, String code) {
        if (userService.selectByUsername(phone) != null) {
            return getErrorMsg(ErrorMsgEnum.PHONE_EXIST);
        }
        if (!isCodeMatch(code, openId)) {
            return getErrorMsg(ErrorMsgEnum.WRONG_CHECK_CODE);
        } else {
            //cacheDao.delete(CODE_PREFIX + openId);
        }
        User user = new User();
        user.setUsername(phone);
        user.setPassword(CommonUtil.String.MD5(password));
        user.setCreateTime(new Date());
        user.setUserType(UserTypeEnum.USER.getType());
        user.setDeleted(false);
        user.setNickName(phone);
        userService.save(user);

        user = userService.selectByUsername(phone);
        RelUserApp relUserApp = new RelUserApp();
        relUserApp.setAppId(appId);
        relUserApp.setPhone(phone);
        relUserApp.setOpenId(openId);
        relUserApp.setUserId(user.getId());
        relUserAppService.save(relUserApp);

        return super.getSuccessResult(1);
    }

    @RequestMapping("/register/sendCode")
    public String register(String openId, String phone) throws ClientException {
        if (userService.selectByPhoneOrUsername(phone) != null) {
            return getErrorMsg(ErrorMsgEnum.PHONE_EXIST);
        }
        sendCode(openId, phone);
        return getSuccessResult(1);
    }

    @RequestMapping("/login/sendCode")
    public String loginSendCode(String openId, String phone) throws ClientException {
        if (userService.selectByPhoneOrUsername(phone) == null) {
            return getErrorMsg(ErrorMsgEnum.PHONE_NOT_EXIST);
        }
        sendCode(openId, phone);
        return getSuccessResult(1);
    }


}
