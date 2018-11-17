package com.yniot.lms.db.dao;

import com.yniot.lms.db.pojo.Role;
import com.yniot.lms.db.pojo.RoleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface RoleMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<Role> {
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

    @Select("select * from sys_role where id in(select role_id from rel_user_role where user_id ={#id})")
    List<Role> selectRoleByUserId(Integer userId);
}