package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.User;

public interface UserService extends IService<User> {

    User selectByUsername(String username);


    int changePassword(String username, String oldPassword, String newPassword);

    User login(String username,String password);

}
