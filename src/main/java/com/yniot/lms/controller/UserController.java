package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.LoginHistoryService;
import com.yniot.lms.service.RelUserAppService;
import com.yniot.lms.service.UserService;
import com.yniot.lms.service.WeChatService;
import com.yniot.lms.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UserController.USER_URI, produces = "text/plain;charset=UTF-8")
public class UserController extends BaseControllerT<User> {
    public final static String USER_URI = "/user";
    private static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    UserService userService;
    //0.普通用户 10.洗衣店配送员   20.洗衣店管理员 25.洗衣店管理员和配送员  30.系统运营人员
    @Autowired
    LoginHistoryService loginHistoryService;
    @Autowired
    WeChatService weChatService;

    public final static String LOGIN_URI = "/login";
    public final static String LOGIN_FULL_URL = USER_URI + LOGIN_URI;

    //0.登陆
    @RequestMapping(LOGIN_URI)
    public String login(@RequestBody User user) throws Exception {
        if (user == null || !CommonUtil.String.validStr(user.getPassword(), user.getUsername())) {
            return super.getErrorMsg("请正确输入账号密码");
        }
        String password = user.getPassword();
        String username = user.getUsername();
        String passwordMD5 = CommonUtil.String.MD5(user.getPassword());
        if (!CommonUtil.String.validStr(username, password)) {
            return super.getErrorResult("请输入正确的账号密码");
        }
        user = userService.login(username, passwordMD5);
        if (user == null) {
            return super.getErrorResult("账户或密码错误!");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, passwordMD5);
        subject.login(token);
        Session session = subject.getSession();
        String host = session.getHost();
        String sessionId = session.getId().toString();
        loginHistoryService.saveLoginInfo(sessionId, host, user, true);
        weChatService.sendLoginNotice(user.getWxOpenid(), host);
        return super.tokenAndUser(session.getId().toString(), user);
    }

    //3.退出登陆
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String host = session.getHost();
        String sessionId = session.getId().toString();
        logger.info("退出登陆:" + sessionId);
        loginHistoryService.saveLoginInfo(sessionId, host, null, false);
        subject.logout();
        return super.getSuccessResult(1);
    }


    //1.密码修改
    @RequestMapping("/changePsw")
    public String changePsw(@RequestParam(name = "username") String username,
                            @RequestParam(name = "oldPassword") String oldPassword,
                            @RequestParam(name = "newPassword") String newPassword) {
        if (StringUtils.isNotEmpty(username) && username.equals(getUser().getUsername())) {
            return super.getSuccessResult(userService.changePassword(username, oldPassword, newPassword));
        } else {
            return noAuth();
        }

    }

    /**
     * 信息修改
     *
     * @param user
     * @return
     */
    @RequestMapping("/changeInfo")
    public String changeInfo(@RequestBody User user) {
        return super.getSuccessResult(userService.saveOrUpdate(user));
    }


    @Autowired
    RelUserAppService relUserAppService;

    @RequestMapping("/relateUserIdNOpenId")
    public String createRel(@RequestParam("appId") String appId,
                            @RequestParam("openId") String openId,
                            @RequestParam("userId") int userId,
                            @RequestParam(name = "phone", required = false, defaultValue = "") String phone) {
        return getSuccessResult(relUserAppService.createRel(appId, openId, userId, phone));
    }

}
