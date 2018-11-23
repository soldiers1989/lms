package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Order;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends CommonMapper<Order> {

}