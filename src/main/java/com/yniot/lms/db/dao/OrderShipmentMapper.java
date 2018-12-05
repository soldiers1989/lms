package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.OrderShipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderShipmentMapper extends CommonMapper<OrderShipment> {


    @Update("update biz_order_shipment set state = #{state} where order_id= #{orderId}")
    int updateState(int orderId, int state);
}