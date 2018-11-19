package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.UserService;
import com.yniot.lms.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    UserService userService;


    //0.登陆
    @PostMapping("/login")
    public String login(@RequestBody User user) throws Exception {
        if (user == null) {
            return super.getErrorMsg("请正确输入账号密码");
        }
        String password = user.getPassword();
        String username = user.getUsername();
        if (!CommonUtil.String.validStr(username, password)) {
            return super.getErrorResult("请输入正确的账号密码");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, CommonUtil.String.MD5(user.getPassword()));
        subject.login(token);
        logger.info("token:" + token);
        return super.getToken(subject.getSession().getId().toString());
    }

    //1.密码修改
    @PostMapping("/changePsw")
    public String changePsw(@RequestParam(name = "username") String username,
                            @RequestParam(name = "oldPassword") String oldPassword,
                            @RequestParam(name = "newPassword") String newPassword) {
        return super.getSuccessResult(userService.changePassword(username, oldPassword, newPassword));
    }


    //2.信息修改


    //3.退出登陆


}
