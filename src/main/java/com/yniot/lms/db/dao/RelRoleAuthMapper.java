package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.RelRoleAuth;
import com.yniot.lms.db.entity.RelRoleAuthExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RelRoleAuthMapper extends CommonMapper<RelRoleAuth> {
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