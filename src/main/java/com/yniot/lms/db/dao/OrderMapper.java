package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Order;

import com.yniot.lms.enums.OrderStateEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper extends CommonMapper<Order> {


    @Update("UPDATE biz_order SET expired = 1 WHERE expired = 0 AND SYSDATE( ) > expired_time AND deleted = 0 AND state = 10")
    int markExpiredOrder();
}