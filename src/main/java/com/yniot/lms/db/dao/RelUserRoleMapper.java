package com.yniot.lms.db.dao;

import com.yniot.lms.db.pojo.RelUserRole;
import com.yniot.lms.db.pojo.RelUserRoleExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RelUserRoleMapper {
    long countByExample(RelUserRoleExample example);

    int deleteByExample(RelUserRoleExample example);

    int insert(RelUserRole record);

    int insertSelective(RelUserRole record);

    List<RelUserRole> selectByExample(RelUserRoleExample example);

    int updateByExampleSelective(@Param("record") RelUserRole record, @Param("example") RelUserRoleExample example);

    int updateByExample(@Param("record") RelUserRole record, @Param("example") RelUserRoleExample example);
}