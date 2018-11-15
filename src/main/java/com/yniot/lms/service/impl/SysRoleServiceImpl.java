package com.yniot.lms.service.impl;

import com.yniot.lms.db.dao.SysRoleMapper;
import com.yniot.lms.db.pojo.SysRole;
import com.yniot.lms.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> selectRoleByUserId(int userId) {
        return sysRoleMapper.selectRoleByUserId(userId);
    }
}
