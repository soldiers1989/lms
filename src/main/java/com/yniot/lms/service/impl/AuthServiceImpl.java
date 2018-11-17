package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.AuthMapper;
import com.yniot.lms.db.entity.Auth;
import com.yniot.lms.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements AuthService {


    @Override
    public List<Auth> selectByUserId(int userId) {
        return baseMapper.selectByUserId(userId);
    }
}
