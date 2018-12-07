package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Role;
import com.yniot.lms.enums.RoleEnum;

import java.util.List;


public interface RoleService extends IService<Role> {
    List<Role> selectRoleByUserId(int userId);

    boolean isLaundry(int userId);

    boolean isMailMan(int userId);

    boolean isAdmin(int userId);

    boolean isUser(int userId);
}
