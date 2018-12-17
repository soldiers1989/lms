package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Role;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper extends CommonMapper<Role> {

    @Select("select * from sys_role where id in (select role_id from rel_user_role where user_id = #{id})")
    List<Role> selectRoleByUserId(@Param("userId") int userId);

    @Select("select count(1) from rel_user_role where role_id = #{roleId}  and user_id = #{userId}  and deleted = 0")
    int hasRole(@Param("roleId") int roleId, @Param("userId") int userId);
}