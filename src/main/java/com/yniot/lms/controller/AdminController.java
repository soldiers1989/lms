package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Auth;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.AuthService;
import com.yniot.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lane
 * @Date: 2018/11/17 15:41
 * @Description:
 * @Version 1.0.0
 */
@RequestMapping("/admin")
@RestController
@AdminOnly
public class AdminController extends BaseControllerT<User> {
    @Autowired
    UserService userService;


    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user) {
        return super.getSuccessResult(userService.save(user));
    }


    /**
     * 获取所有用户
     *
     * @return
     */
    @RequestMapping("/allUser")
    public String getAllUser(@RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") long pageNum,
                             @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "0") long pageSize) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("deleted", "false");
        return super.getSuccessResult(userService.list(userQueryWrapper));
    }


    //1.crud衣物类型

    //2.更改权限




}
