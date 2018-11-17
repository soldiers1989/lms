package com.yniot.lms.db.dao;

import com.yniot.lms.db.pojo.RelRoleAuth;
import com.yniot.lms.db.pojo.RelRoleAuthExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RelRoleAuthMapper  extends com.baomidou.mybatisplus.core.mapper.BaseMapper<RelRoleAuthMapper> {
    long countByExample(RelRoleAuthExample example);

    int deleteByExample(RelRoleAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RelRoleAuth record);

    int insertSelective(RelRoleAuth record);

    List<RelRoleAuth> selectByExample(RelRoleAuthExample example);

    RelRoleAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RelRoleAuth record, @Param("example") RelRoleAuthExample example);

    int updateByExample(@Param("record") RelRoleAuth record, @Param("example") RelRoleAuthExample example);

    int updateByPrimaryKeySelective(RelRoleAuth record);

    int updateByPrimaryKey(RelRoleAuth record);
}