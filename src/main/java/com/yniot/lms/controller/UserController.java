package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.LoginHistoryService;
import com.yniot.lms.service.UserService;
import com.yniot.lms.utils.CommonUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
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
@RequestMapping(value = "/user", produces = "text/plain;charset=UTF-8")
public class UserController extends BaseControllerT<User> {
    private static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    UserService userService;
    //0.普通用户 10.洗衣店配送员   20.洗衣店管理员 25.洗衣店管理员和配送员  30.系统运营人员
    @Autowired
    LoginHistoryService loginHistoryService;
    @Autowired
    WxMpService wxMpService;


    //0.登陆
    @RequestMapping("/login")
    public String login(@RequestBody User user) throws Exception {
        if (user == null) {
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

        //218FmLW3m6DOiFpfvQp3ev148Jy6yhahzk9cpIe0Fq4
        //    亲爱的姓名，您已经成功登陆某某管理系统
        //    登陆时间：2015年5月22日9:04:56
        //    登陆Ip：183.185.98.240
        //    如果不是您本人操作请联系系统管理员。


        //    {{first.DATA}}
        //    登陆时间：{{keyword1.DATA}}
        //    登陆Ip：{{keyword2.DATA}}
        //    {{remark.DATA}}
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("218FmLW3m6DOiFpfvQp3ev148Jy6yhahzk9cpIe0Fq4");
        wxMpTemplateMessage.setUrl("https://www.baidu.com");
        wxMpTemplateMessage.setToUser("oaend0YgraAE8JpUNSt4YN4tvZEk");
        wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        return super.getToken(session.getId().toString());
    }

    //3.退出登陆
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String host = session.getHost();
        String sessionId = session.getId().toString();
        logger.info("退出登陆:"+sessionId);
        loginHistoryService.saveLoginInfo(sessionId, host, null, false);
        subject.logout();
        return super.getSuccessResult(1);
    }

    //1.密码修改
    @RequestMapping("/changePsw")
    public String changePsw(@RequestParam(name = "username") String username,
                            @RequestParam(name = "oldPassword") String oldPassword,
                            @RequestParam(name = "newPassword") String newPassword) {
        return super.getSuccessResult(userService.changePassword(username, oldPassword, newPassword));
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


}
