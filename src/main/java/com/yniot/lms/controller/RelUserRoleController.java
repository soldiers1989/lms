package com.yniot.lms.controller;

import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.RelUserRole;
import com.yniot.lms.service.RelUserRoleService;
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
@RequestMapping("/relUserRole")
public class RelUserRoleController extends BaseControllerT<RelUserRole> {
    @Autowired
    RelUserRoleService relUserRoleService;


    @RequestMapping("/relate")
    public String relate(@RequestBody RelUserRole relUserRole) {
        return super.getSuccessResult(relUserRoleService.save(relUserRole));
    }

    @RequestMapping("/unRelate")
    public String unRelate(@RequestParam(name = "relUserRoleId") int relUserRoleId) {
        return super.getSuccessResult(relUserRoleService.removeById(relUserRoleId));
    }
}
