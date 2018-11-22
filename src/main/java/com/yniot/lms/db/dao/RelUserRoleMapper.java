package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.RelUserRole;
import com.yniot.lms.db.entity.RelUserRoleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RelUserRoleMapper extends CommonMapper<RelUserRole> {
    long countByExample(RelUserRoleExample example);

    int deleteByExample(RelUserRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RelUserRole record);

    int insertSelective(RelUserRole record);

    List<RelUserRole> selectByExample(RelUserRoleExample example);

    RelUserRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RelUserRole record, @Param("example") RelUserRoleExample example);

    int updateByExample(@Param("record") RelUserRole record, @Param("example") RelUserRoleExample example);

    int updateByPrimaryKeySelective(RelUserRole record);

    int updateByPrimaryKey(RelUserRole record);
}