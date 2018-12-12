package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminAndLaundry;
import com.yniot.lms.annotation.Unfinished;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Application;
import com.yniot.lms.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 15:13
 **/
@RestController
@RequestMapping("/application")
@Unfinished
public class ApplicationController extends BaseControllerT<Application> {
    //state 10.申请提交，等待审核  20.审核通过 30.审核不过
    private static final int WAITING = 10;
    private static final int PASSED = 20;
    private static final int DENIED = 30;


    //req_type 1.配送员申请,2.洗衣店申请
    private static final int MAIL_MAN_APPLY = 1;
    private static final int LAUNDRY_MAN_APPLY = 2;


    @Autowired
    ApplicationService applicationService;

    @RequestMapping("/create")
    public String createApplication(@RequestBody Application application) {
        application.setCreateTime(new Date());
        return super.getSuccessResult(applicationService.save(application));
    }


    @RequestMapping("/approve")
    public String approveApplication(@RequestParam(name = "applicationIdList[]") List<Integer> applicationIdList) {
        return super.getSuccessResult(applicationService.approveOrDeny(true, applicationIdList));
    }

    @AdminAndLaundry
    @RequestMapping("/deny")
    public String deniedApplication(@RequestParam(name = "applicationIdList[]") List<Integer> applicationIdList) {
        return super.getSuccessResult(applicationService.approveOrDeny(false, applicationIdList));
    }

    @RequestMapping("/select")
    public String selectApplication(
            @RequestParam(name = "userId", required = false, defaultValue = "0") int userId,
            @RequestParam(name = "state", required = false, defaultValue = "0") int state,
            @RequestParam(name = "type", required = false, defaultValue = "0") int type,
            @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "20") int pageSize,
            @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<Application> applicationWrapper = new QueryWrapper<Application>();
        applicationWrapper.eq("deleted", "false");
        if (userId > 0) {
            applicationWrapper.eq("user_id", userId);
        }
        if (state > 0) {
            applicationWrapper.eq("state", state);
        }
        if (type > 0) {
            applicationWrapper.eq("req_type", type);
        }
        if (pageSize > 0) {
            Page<Application> applicationPage = new Page();
            applicationPage.setSize(pageSize);
            applicationPage.setPages(pageNum);
            applicationPage.setDesc("create_time");
            return super.getSuccessPage(applicationService.page(applicationPage, applicationWrapper));
        } else {
            return super.getSuccessResult(applicationService.list(applicationWrapper));
        }

    }

    @RequestMapping("/selectByOpenId")
    public String selectApplication(@RequestParam(name = "openId") int openId) {
        QueryWrapper<Application> applicationWrapper = new QueryWrapper<Application>();
        applicationWrapper.eq("deleted", "false");
        applicationWrapper.eq("open_id", openId);
        return super.getSuccessResult(applicationService.list(applicationWrapper));
    }


}
