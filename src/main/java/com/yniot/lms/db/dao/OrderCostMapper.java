package com.yniot.lms.db.dao;

import com.yniot.lms.db.entity.OrderCost;
import com.yniot.exclude.CommonMapper;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
public interface OrderCostMapper extends CommonMapper<OrderCost> {
    @Update("UPDATE biz_order_cost SET confirmed = 1,act_paid_cost =#{actPaidCost},confirm_time = sysdate( )  WHERE id = #{orderId}")
    int paid(BigDecimal actPaidCost, int orderId);
}
