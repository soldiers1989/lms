package com.yniot.lms.service.impl;

import com.yniot.lms.db.entity.OrderCost;
import com.yniot.lms.db.dao.OrderCostMapper;
import com.yniot.lms.service.OrderCostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
@Service
public class OrderCostServiceImpl extends ServiceImpl<OrderCostMapper, OrderCost> implements OrderCostService {

    @Override
    public boolean save(int orderId, BigDecimal estCost) {
        OrderCost orderCost = new OrderCost();
        orderCost.setId(orderId);
        orderCost.setEstTotalCost(estCost);
        orderCost.setActPaidCost(estCost);
        orderCost.setActTotalCost(estCost);
        orderCost.setConfirmed(false);
        orderCost.setGenerateTime(LocalDateTime.now());
        orderCost.setExtCost(new BigDecimal(0));
        return save(orderCost);
    }
    @Override
    public boolean paid(int orderId, BigDecimal price) {
        return baseMapper.paid(price, orderId) > 0;
    }
}
