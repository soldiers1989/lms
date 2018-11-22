package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Role;

import java.util.List;


public interface RoleService extends IService<Role> {
    List<Role> selectRoleByUserId(int userId);
}
