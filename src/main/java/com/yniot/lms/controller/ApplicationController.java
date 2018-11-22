package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Application;
import com.yniot.lms.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 15:13
 **/
@RestController
@RequestMapping("/application")
public class ApplicationController extends BaseControllerT<Application> {
    //state 10.申请提交，等待审核  20.审核通过 30.审核不过

    //req_type 1.配送员申请,2.洗衣店申请
    private static final int WAITING = 10;
    private static final int PASSED = 20;
    private static final int DENIED = 30;


    @Autowired
    ApplicationService applicationService;

    @RequestMapping("/create")
    public String createApplication(@RequestBody Application application) {
        return super.getSuccessResult(applicationService.save(application));
    }


    @RequestMapping("/update")
    public String updateApplication(@RequestBody Application application) {
        return super.getSuccessResult(applicationService.saveOrUpdate(application));
    }

    @RequestMapping("/select")
    public String selectApplication(@RequestParam(name = "state", required = false, defaultValue = "10") int state,
                                    @RequestParam(name = "type", required = false, defaultValue = "0") int type,
                                    @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "0") int pageSize,
                                    @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<Application> applicationWrapper = new QueryWrapper<Application>();
        applicationWrapper.eq("deleted", "false");
        applicationWrapper.eq("state", WAITING);
        if (type > 0) {
            applicationWrapper.eq("req_type", type);
        }
        Page<Application> applicationPage = new Page();
        applicationPage.setSize(pageSize);
        applicationPage.setPages(pageNum);
        applicationPage.setDesc("create_time");
        return super.getSuccessPage(applicationService.page(applicationPage, applicationWrapper));
    }
}
