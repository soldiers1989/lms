package com.yniot.lms.db.dao;

import com.yniot.lms.db.pojo.RelRoleAuth;
import com.yniot.lms.db.pojo.RelRoleAuthExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RelRoleAuthMapper {
    long countByExample(RelRoleAuthExample example);

    int deleteByExample(RelRoleAuthExample example);

    int insert(RelRoleAuth record);

    int insertSelective(RelRoleAuth record);

    List<RelRoleAuth> selectByExample(RelRoleAuthExample example);

    int updateByExampleSelective(@Param("record") RelRoleAuth record, @Param("example") RelRoleAuthExample example);

    int updateByExample(@Param("record") RelRoleAuth record, @Param("example") RelRoleAuthExample example);
}