package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderMapper extends CommonMapper<Order> {


    @Update("UPDATE biz_order SET state=#{state} WHERE SYSDATE() > expired_time AND deleted = 0 AND state = 10 ")
    int markExpiredOrder(int state);

    int updateState(int orderId, int state);

    int updateStateBatch(@Param("list") List<Integer> orderIdList, int state);

    @Update("UPDATE biz_order SET state = 60 WHERE id = #{orderId} AND state = 55")
    int paid(int orderId);

    int paid_procedure(BigDecimal price, String transactionId, String tradeType);

    List<Map<String, Object>> getFullDetail(@Param("list") List<Integer> orderIdList);


    List<Map<String, Integer>> getStatisticInfo(@Param("laundryId") int laundryId);
}