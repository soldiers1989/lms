package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Auth;
import com.yniot.lms.service.AuthService;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping("/create")
    public String createAuth(@RequestBody Auth auth) {
        return super.getSuccessResult(authService.save(auth));
    }

    @RequestMapping("/update")
    public String updateAuth(@RequestBody Auth auth) {
        return super.getSuccessResult(authService.saveOrUpdate(auth));
    }

    @RequestMapping("/delete")
    public String deleteAuth(@RequestParam(name = "authId") int authId) {
        return super.getSuccessResult(authService.removeById(authId));
    }

    @RequestMapping("/select")
    public String selectAuth(@RequestParam(name = "keyWord", required = false, defaultValue = "") String keyWord,
                             @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") long pageNum,
                             @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "0") long pageSize) {
        QueryWrapper<Auth> authQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            authQueryWrapper.like("", keyWord);
        }
        return super.getSuccessPage(authService.page(super.getPage(new Page(), pageNum, pageSize), authQueryWrapper));
    }

}
