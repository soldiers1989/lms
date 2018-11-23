package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.dao.LoginHistoryMapper;
import com.yniot.lms.db.entity.LoginHistory;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-23 09:14
 **/
@Service
public class LoginHistoryImpl extends ServiceImpl<LoginHistoryMapper, LoginHistory> implements LoginHistoryService {

    /**
     * @return boolean
     * @Author wanggl
     * @Description 保存登陆记录, 并将用户信息保存到缓存
     * @create 2018-11-23
     * @modify 2018-11-23
     * @Param [token, host, user, login]
     **/
    @Override
    public boolean saveLoginInfo(String token, String host, User user, boolean login) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setToken(token);
        loginHistory.setIp(host);
        loginHistory.setCreateTime(new Date());
        if (login && user != null) {
            user.setPassword("");
            loginHistory.setUsername(user.getUsername());
            loginHistory.setUserId(user.getId());
        } else {
        }
        loginHistory.setLogin(login);
        return super.save(loginHistory);
    }
}
