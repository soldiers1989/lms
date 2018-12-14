package com.yniot.lms.service;

import com.yniot.lms.db.entity.OrderCost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
public interface OrderCostService extends IService<OrderCost> {
    boolean save(int orderId, BigDecimal estCost);

    boolean paid(int orderId, BigDecimal price);
}
