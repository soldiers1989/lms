package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.GoodsCode;
import com.yniot.lms.db.entity.GoodsCodeExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GoodsCodeMapper extends CommonMapper<GoodsCode> {
    long countByExample(GoodsCodeExample example);

    int deleteByExample(GoodsCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsCode record);

    int insertSelective(GoodsCode record);

    List<GoodsCode> selectByExample(GoodsCodeExample example);

    GoodsCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsCode record, @Param("example") GoodsCodeExample example);

    int updateByExample(@Param("record") GoodsCode record, @Param("example") GoodsCodeExample example);

    int updateByPrimaryKeySelective(GoodsCode record);

    int updateByPrimaryKey(GoodsCode record);
}