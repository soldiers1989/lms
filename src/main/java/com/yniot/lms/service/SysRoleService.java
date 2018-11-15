package com.yniot.lms.service;

import com.yniot.lms.db.pojo.SysRole;

import java.util.List;


public interface SysRoleService {
    List<SysRole> selectRoleByUserId(int userId);
}
