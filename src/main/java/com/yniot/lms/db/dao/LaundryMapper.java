package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Laundry;
import com.yniot.lms.db.entity.LaundryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface LaundryMapper extends CommonMapper<Laundry> {
    long countByExample(LaundryExample example);

    int deleteByExample(LaundryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Laundry record);

    int insertSelective(Laundry record);

    List<Laundry> selectByExample(LaundryExample example);

    Laundry selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Laundry record, @Param("example") LaundryExample example);

    int updateByExample(@Param("record") Laundry record, @Param("example") LaundryExample example);

    int updateByPrimaryKeySelective(Laundry record);

    int updateByPrimaryKey(Laundry record);
}