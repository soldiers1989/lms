package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Role;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper extends CommonMapper<Role> {

    @Select("select * from sys_role where id in (select role_id from rel_user_role where user_id = #{id})")
    List<Role> selectRoleByUserId(int userId);

    @Select("select count(1) from rel_user_role where user_id = #{userId} and role_id = #{roleId} and deleted = 0")
    int hasRole(int roleId, int userId);
}