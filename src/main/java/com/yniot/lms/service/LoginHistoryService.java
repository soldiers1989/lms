package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.LoginHistory;
import com.yniot.lms.db.entity.User;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-23 09:13
 **/
public interface LoginHistoryService extends IService<LoginHistory> {

    boolean saveLoginInfo(String token, String host, User user, boolean login);
}
