package com.yniot.lms.controller;

import com.yniot.lms.annotation.WithoutAuth;
import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.pojo.User;
import com.yniot.lms.security.ShiroConfigurator;
import com.yniot.lms.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {
    private static Logger logger = Logger.getLogger(UserController.class);


    @RequestMapping(name = "/user/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password) throws Exception {
        if (!CommonUtil.String.validStr(username, password)) {
            return super.getErrorResult("请输入正确的账号密码");
        }
        Subject subject = SecurityUtils.getSubject();
        User user = new User();
        user.setPassword(CommonUtil.String.MD5(user.getPassword()));
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        subject.login(token);
        logger.info("token:"+token);
        return super.getSuccessResult(token);
    }
    @RequestMapping(name = "/user/unlogin")
    public String unlogin() throws Exception {
        return super.getErrorResult("请先登录！");
    }
    @RequestMapping(name = "/user/unauth")
    public String unAuth() throws Exception {
        return super.getErrorResult("未授权！");
    }

    @RequestMapping(name = "/user/index")
    public String index() throws Exception {
        return super.getSuccessResult("登陆成功！");
    }
}
