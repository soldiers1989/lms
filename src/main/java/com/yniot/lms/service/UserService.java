package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {

    User selectByUsername(String username);

    User selectByUserId(int userId);

    Page<User> selectUserByCondition(Map<String, Object> condition);

    int updateUserInfo(User user);

    int changePassword(String username, String oldPassword, String newPassword);

    int addUser(User user);

    int deleteUser(String username);

    int deleteUser(int id);

    int relateRole(String userId,int roleId,boolean relate);



}
