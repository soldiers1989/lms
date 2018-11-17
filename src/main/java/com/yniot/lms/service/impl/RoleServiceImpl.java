package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.RoleMapper;
import com.yniot.lms.db.pojo.Role;
import com.yniot.lms.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> selectRoleByUserId(int userId) {
        return baseMapper.selectRoleByUserId(userId);
    }
}
