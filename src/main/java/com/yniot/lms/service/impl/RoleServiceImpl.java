package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.RoleMapper;
import com.yniot.lms.db.entity.Role;
import com.yniot.lms.enums.RoleEnum;
import com.yniot.lms.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> selectRoleByUserId(int userId) {
        return baseMapper.selectRoleByUserId(userId);
    }

    @Override
    public boolean isLaundry(int userId) {
        return baseMapper.hasRole(RoleEnum.LAUNDRY.getRoleId(), userId) > 0;
    }

    @Override
    public boolean isMailMan(int userId) {
        return baseMapper.hasRole(RoleEnum.MAILMAN.getRoleId(), userId) > 0;
    }

    @Override
    public boolean isAdmin(int userId) {
        return baseMapper.hasRole(RoleEnum.ADMIN.getRoleId(), userId) > 0;
    }

    @Override
    public boolean isUser(int userId) {
        return baseMapper.hasRole(RoleEnum.USER.getRoleId(), userId) > 0;
    }
}
