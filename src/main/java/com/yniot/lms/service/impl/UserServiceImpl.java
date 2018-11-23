package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.UserMapper;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User selectByUsername(String username) {
        List<User> userList = userMapper.selectByUsername(username);
        User result = null;
        if (userList != null && userList.size() > 0) {
            result = userList.get(0);
        }
        return result;
    }


    @Override
    public int changePassword(String username, String oldPassword, String newPassword) {
        User user = new User();
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User login(String username, String passwordMD5) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("password",passwordMD5);
        userQueryWrapper.eq("username",username);
        return baseMapper.selectOne(userQueryWrapper);
    }


}
