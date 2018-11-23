package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.OrderStateHistory;
import com.yniot.lms.db.entity.OrderStateHistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface OrderStateHistoryMapper extends CommonMapper<OrderStateHistory> {
    long countByExample(OrderStateHistoryExample example);

    int deleteByExample(OrderStateHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderStateHistory record);

    int insertSelective(OrderStateHistory record);

    List<OrderStateHistory> selectByExample(OrderStateHistoryExample example);

    OrderStateHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderStateHistory record, @Param("example") OrderStateHistoryExample example);

    int updateByExample(@Param("record") OrderStateHistory record, @Param("example") OrderStateHistoryExample example);

    int updateByPrimaryKeySelective(OrderStateHistory record);

    int updateByPrimaryKey(OrderStateHistory record);
}