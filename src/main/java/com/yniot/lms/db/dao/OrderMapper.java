package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Order;

import com.yniot.lms.enums.OrderStateEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import sun.jvm.hotspot.debugger.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends CommonMapper<Order> {


    @Update("UPDATE biz_order SET state=#{state} WHERE SYSDATE() > expired_time AND deleted = 0 AND state = 10 ")
    int markExpiredOrder(int state);

    @Update("UPDATE biz_order SET state=#{state} where id = #{orderId}")
    int updateState(int orderId, int state);


}