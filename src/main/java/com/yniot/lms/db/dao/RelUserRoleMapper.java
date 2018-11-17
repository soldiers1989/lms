package com.yniot.lms.db.dao;

import com.yniot.lms.db.pojo.RelUserRole;
import com.yniot.lms.db.pojo.RelUserRoleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RelUserRoleMapper  extends com.baomidou.mybatisplus.core.mapper.BaseMapper<RelUserRole> {
    long countByExample(RelUserRoleExample example);

    int deleteByExample(RelUserRoleExample example);

    int insert(RelUserRole record);

    int insertSelective(RelUserRole record);

    List<RelUserRole> selectByExample(RelUserRoleExample example);

    int updateByExampleSelective(@Param("record") RelUserRole record, @Param("example") RelUserRoleExample example);

    int updateByExample(@Param("record") RelUserRole record, @Param("example") RelUserRoleExample example);
}