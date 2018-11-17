package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.db.entity.Role;

import java.util.List;


public interface RoleService extends IService<Role> {
    List<Role> selectRoleByUserId(int userId);

    Page<Role> selectAllRole();

    int relateRole(String username, int roleId);
}
