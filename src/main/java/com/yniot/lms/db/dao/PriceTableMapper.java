package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.PriceTable;
import com.yniot.lms.db.entity.PriceTableExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PriceTableMapper extends CommonMapper<PriceTable> {
    long countByExample(PriceTableExample example);

    int deleteByExample(PriceTableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PriceTable record);

    int insertSelective(PriceTable record);

    List<PriceTable> selectByExample(PriceTableExample example);

    PriceTable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PriceTable record, @Param("example") PriceTableExample example);

    int updateByExample(@Param("record") PriceTable record, @Param("example") PriceTableExample example);

    int updateByPrimaryKeySelective(PriceTable record);

    int updateByPrimaryKey(PriceTable record);
}