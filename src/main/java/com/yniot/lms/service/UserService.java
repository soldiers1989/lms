package com.yniot.lms.service;

import com.yniot.lms.db.pojo.User;

public interface UserService {

    User selectByUsername(String username);
}
