package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lane
 * @Date: 2018/11/17 15:41
 * @Description:
 * @Version 1.0.0
 */
@RequestMapping("/admin")
@RestController
public class AdminController extends BaseController {
    @Autowired
    UserService userService;


    /**
     * 添加用户
     * @param user
     * @return
     */
    @AdminOnly
    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user) {
        return super.getSuccessResult(userService.save(user));
    }


    /**
     * 获取所有用户
     * @return
     */
    @AdminOnly
    @RequestMapping("/allUser")
    public String getAllUser() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("deleted", "false");
        return super.getSuccessResult(userService.list(userQueryWrapper));
    }


    //1.新增衣物类型


    //2.

}
