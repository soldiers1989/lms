package com.yniot.lms.controller;

import com.yniot.lms.annotation.AdminOnly;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 18:41
 **/
@AdminOnly
@RestController
@RequestMapping("/role")
public class RoleController {
    //3.crud角色
}
