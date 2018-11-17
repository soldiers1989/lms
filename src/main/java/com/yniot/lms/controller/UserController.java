package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.pojo.User;
import com.yniot.lms.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private static Logger logger = Logger.getLogger(UserController.class);

    //0.登陆
    @RequestMapping(name = "/login")
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
    //1.密码修改
    //2.信息修改
    //3.退出登陆



}
