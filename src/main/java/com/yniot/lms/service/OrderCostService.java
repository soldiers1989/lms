package com.yniot.lms.service;

import com.yniot.lms.db.entity.OrderCost;
import com.baomidou.mybatisplus.extension.service.IService;
import me.chanjar.weixin.common.error.WxErrorException;

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
    boolean saveEstCost(int orderId, BigDecimal estCost);

    boolean commitPrice(int orderId, BigDecimal actCost) throws WxErrorException;

    boolean paid(int orderId, BigDecimal price);
}
