package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Order;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface OrderMapper extends CommonMapper<Order> {


    @Update("UPDATE biz_order SET state=#{state} WHERE SYSDATE() > expired_time AND deleted = 0 AND state = 10 ")
    int markExpiredOrder(int state);

//    @Update("UPDATE biz_order SET state=#{state} where id = #{orderId}")
    int updateState(int orderId, int state);

    @Update("UPDATE biz_order SET state = 60 WHERE id = #{orderId} AND state = 55")
    int paid(int orderId);

    int paid_procedure(int orderId, BigDecimal price);

}