package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Role;
import com.yniot.lms.db.entity.RoleExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper extends CommonMapper<Role> {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);


    @Select("select * from sys_role where id in (select role_id from rel_user_role where user_id = #{id})")
    List<Role> selectRoleByUserId(int userId);
}