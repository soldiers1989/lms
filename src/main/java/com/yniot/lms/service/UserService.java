package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.User;

public interface UserService extends IService<User> {

    User selectByUsername(String username);

    User selectByPhoneOrUsername(String phone);

    User login(String username, String password);

    int changePassword(String username, String oldPassword, String newPassword);

    User selectByAppIdNOpenId(String appId, String openId);

    String getOpenIdByLaundryId(int laundryId);

}
