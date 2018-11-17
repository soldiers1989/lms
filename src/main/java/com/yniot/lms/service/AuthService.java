package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Auth;

import java.util.List;

public interface AuthService extends IService<Auth> {
    List<Auth> selectByUserId(int userId);

}
