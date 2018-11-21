package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.UserMapper;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.db.entity.UserExample;
import com.yniot.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public User selectUser(String username, String password) {
        UserExample userExample = new UserExample();
        userExample.or().andPasswordEqualTo(password).andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(userExample);
        User result = null;
        if (userList != null && userList.size() > 0) {
            result = userList.get(0);
        }
        return result;
    }

    @Override
    public User selectByUserId(int userId) {
        return null;
    }

    @Override
    public Page<User> selectUserByCondition(Map<String, Object> condition) {
        return null;
    }

    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int changePassword(String username, String oldPassword, String newPassword) {
        User user = new User();
        return userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }


    /**
     * 删除用户
     * @param username
     * @return
     */
    @Override
    public int deleteUser(String username) {

        return 0;
    }

    @Override
    public int deleteUser(int id) {

        return 0;
    }

    @Override
    public int relateRole(String userId, int roleId, boolean relate) {
        return 0;
    }
}
