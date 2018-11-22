package com.yniot.lms.controller;

import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.RelRoleAuth;
import com.yniot.lms.service.RelRoleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 19:20
 **/
@AdminOnly
@RestController
@RequestMapping("/relRoleAuth")
public class RelRoleAuthController extends BaseControllerT<RelRoleAuth> {
    @Autowired
    RelRoleAuthService relRoleAuthService;


    @RequestMapping("/relate")
    public String relate(@RequestBody RelRoleAuth relRoleAuth) {
        return super.getSuccessResult(relRoleAuthService.save(relRoleAuth));
    }

    @RequestMapping("/unRelate")
    public String unRelate(@RequestParam(name = "relRoleAuthId") int relRoleAuthId) {
        return super.getSuccessResult(relRoleAuthService.removeById(relRoleAuthId));
    }
}
