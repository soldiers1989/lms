package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.UserMapper;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.UserService;
import com.yniot.lms.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public User selectByUsername(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        return baseMapper.selectOne(userQueryWrapper);
    }

    @Override
    public User selectByPhoneOrUsername(String phone) {
        return baseMapper.selectByPhoneOrUsername(phone);
    }

    public User selectByAppIdNOpenId(String appId, String openId) {
        return baseMapper.selectByAppIdNOpenId(appId, openId);
    }


    @Override
    public User login(String username, String passwordMD5) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("password", passwordMD5);
        userQueryWrapper.eq("username", username);
        return baseMapper.selectOne(userQueryWrapper);
    }

    @Override
    public int changePassword(String username, String oldPassword, String newPassword) {
        User user = this.login(username, CommonUtil.String.MD5(oldPassword));
        if (user != null) {
            user.setPassword(CommonUtil.String.MD5(newPassword));
            user.setModifyTime(new Date());
            return super.saveOrUpdate(user) ? 1 : 0;
        }
        return 0;
    }

    @Override
    public String getOpenIdByLaundryId(int laundryId) {
        return getOpenIdByLaundryId(laundryId);
    }


}
