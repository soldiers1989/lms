package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Auth;
import com.yniot.lms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 18:40
 **/
@AdminOnly
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseControllerT<Auth> {
    @Autowired
    AuthService authService;

    //5.crud权限
    @RequestMapping("/auth/create")
    public String createAuth(@RequestBody Auth auth) {
        return super.getSuccessResult(authService.save(auth));
    }

    @RequestMapping("/auth/select")
    public String selectAuth(@RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") long pageNum,
                             @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "0") long pageSize) {
        QueryWrapper<Auth> authQueryWrapper = new QueryWrapper<>();
        authQueryWrapper.eq("deleted", 0);
        return super.getSuccessPage(authService.page(super.getPage(new Page(), pageNum, pageSize), authQueryWrapper));
    }

}
