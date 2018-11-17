package com.yniot.lms.db.dao;

import com.yniot.lms.db.pojo.Auth;
import com.yniot.lms.db.pojo.AuthExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface AuthMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<Auth> {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    @Select("select * from auth where id in(select auth_id from rel_role_auth where role_id in  (select role_id from rel_user_role where user_id={#id}))")
    List<Auth> selectByUserId(int userId);
}