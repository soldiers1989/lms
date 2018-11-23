package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.LoginHistoryMapper;
import com.yniot.lms.db.entity.LoginHistory;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.LoginHistoryService;
import org.springframework.stereotype.Service;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-23 09:14
 **/
@Service
public class LoginHistoryImpl extends ServiceImpl<LoginHistoryMapper, LoginHistory> implements LoginHistoryService {

    @Override
    public boolean saveLoginInfo(String token, String host, User user, boolean login) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setToken(token);
        loginHistory.setIp(host);
        if (login && user != null) {
            loginHistory.setUsername(user.getUsername());
            loginHistory.setId(user.getId());
        }
        loginHistory.setLogin(login);
        return super.save(loginHistory);
    }
}
