package com.yniot.lms.service;

import com.yniot.lms.db.pojo.SysAuth;

import java.util.List;

public interface SysAuthService {
    List<SysAuth> queryByUserId(int userId);

}
