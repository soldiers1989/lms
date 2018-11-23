package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Auth;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface AuthMapper extends CommonMapper<Auth> {
    @Select("select * from sys_auth where id in( select * from sys_role where id in (select role_id from rel_user_role where user_id = #{id}))")
    List<Auth> selectByUserId(Integer id);
}