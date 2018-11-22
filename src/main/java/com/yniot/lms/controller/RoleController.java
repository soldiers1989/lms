package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Role;
import com.yniot.lms.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: lms
 * @description:
 * @roleor: wanggl
 * @create: 2018-11-22 18:41
 **/
@AdminOnly
@RestController
@RequestMapping("/role")
public class RoleController extends BaseControllerT<Role> {
    @Autowired
    RoleService roleService;

    @RequestMapping("/create")
    public String createRole(@RequestBody Role role) {
        return super.getSuccessResult(roleService.save(role));
    }

    @RequestMapping("/update")
    public String updateRole(@RequestBody Role role) {
        return super.getSuccessResult(roleService.saveOrUpdate(role));
    }

    @RequestMapping("/delete")
    public String deleteRole(@RequestParam(name = "roleId") int roleId) {
        return super.getSuccessResult(roleService.removeById(roleId));
    }

    @RequestMapping("/select")
    public String selectRole(@RequestParam(name = "keyWord", required = false, defaultValue = "") String keyWord,
                             @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") long pageNum,
                             @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "0") long pageSize) {
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            roleQueryWrapper.like("", keyWord);
        }
        return super.getSuccessPage(roleService.page(super.getPage(new Page(), pageNum, pageSize), roleQueryWrapper));
    }
}
