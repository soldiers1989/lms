package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends CommonMapper<User> {

    @Select("select * from sys_user where id in(select user_id from rel_user_app where open_id =#{openId} and app_id=#{appId}) limit 0,1")
    User selectByAppIdNOpenId(@Param("appId") String appId, @Param("openId") String openId);


    @Select("select * from sys_user where id in(select user_id from rel_user_app where open_id =#{openId} and app_id=#{appId}) limit 0,1")
    User selectByAppIdNPhone(@Param("appId") String appId, @Param("openId") String openId);

    @Select("select * from sys_user where phone =#{phone} or username = #{phone}")
    User selectByPhoneOrUsername(String phone);

    @Select("select open_id from sys_user where id in(select user_id from biz_laundry where id = #{laundryId})")
    String getOpenIdByLaundryId(int laundryId);
}