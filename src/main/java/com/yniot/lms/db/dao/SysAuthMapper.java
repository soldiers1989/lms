package com.yniot.lms.db.dao;

import com.yniot.lms.db.pojo.SysAuth;
import com.yniot.lms.db.pojo.SysAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysAuthMapper {
    long countByExample(SysAuthExample example);

    int deleteByExample(SysAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysAuth record);

    int insertSelective(SysAuth record);

    List<SysAuth> selectByExample(SysAuthExample example);

    SysAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysAuth record, @Param("example") SysAuthExample example);

    int updateByExample(@Param("record") SysAuth record, @Param("example") SysAuthExample example);

    int updateByPrimaryKeySelective(SysAuth record);

    int updateByPrimaryKey(SysAuth record);
}