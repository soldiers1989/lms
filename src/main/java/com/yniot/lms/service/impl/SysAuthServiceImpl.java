package com.yniot.lms.service.impl;

import com.yniot.lms.db.dao.SysAuthMapper;
import com.yniot.lms.db.pojo.SysAuth;
import com.yniot.lms.db.pojo.SysAuthExample;
import com.yniot.lms.service.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAuthServiceImpl implements SysAuthService {
    @Autowired
    SysAuthMapper sysAuthMapper;

    @Override
    public List<SysAuth> queryByUserId(int userId) {
        return sysAuthMapper.queryByUserId(userId);
    }
}
