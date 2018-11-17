package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.pojo.User;

public interface UserService extends IService<User> {

    User selectByUsername(String username);
}
