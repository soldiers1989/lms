package com.yniot.lms.service.impl;

import com.yniot.lms.db.dao.UserMapper;
import com.yniot.lms.db.pojo.User;
import com.yniot.lms.db.pojo.UserExample;
import com.yniot.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;


    @Override
    public User selectByUsername(String username) {
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        User result = null;
        if (userList != null && userList.size() > 0) {
            result = userList.get(0);
        }
        return result;
    }
}
