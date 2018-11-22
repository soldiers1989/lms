package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Auth;
import com.yniot.lms.db.entity.RelRoleAuth;
import com.yniot.lms.service.AuthService;
import com.yniot.lms.service.RelRoleAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    RelRoleAuthService relRoleAuthService;

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
            authQueryWrapper.like("auth_name", keyWord).or()
                            .like("permission", keyWord)
                            .like("url", keyWord)
                            .like("description", keyWord);
        }
        return super.getSuccessPage(authService.page(new Page(pageNum, pageSize), authQueryWrapper));
    }


    @RequestMapping("/selectByUserId")
    public String selectAuth(@RequestParam(name = "userId") int userId) {
        return super.getSuccessResult(authService.selectByUserId(userId));
    }


    @RequestMapping("/selectByRoleId")
    public String selectAuthByRoleId(@RequestParam(name = "roleIdList[]") List<Integer> roleIdList,
                                     @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") long pageNum,
                                     @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "0") long pageSize) {
        Collection<RelRoleAuth> relRoleAuthList = relRoleAuthService.listByIds(roleIdList);
        Collection<Integer> authIdCollection = relRoleAuthList.stream().map(RelRoleAuth::getAuthId).collect(Collectors.toList());
        QueryWrapper<Auth> authQueryWrapper = new QueryWrapper<>();
        authQueryWrapper.in("id", authIdCollection);
        return super.getSuccessPage(authService.page(new Page(pageNum, pageSize), authQueryWrapper));
    }

}
