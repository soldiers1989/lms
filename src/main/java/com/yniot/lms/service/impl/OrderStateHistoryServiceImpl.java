package com.yniot.lms.service.impl;

import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderStateHistory;
import com.yniot.lms.db.dao.OrderStateHistoryMapper;
import com.yniot.lms.service.OrderStateHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
@Service
public class OrderStateHistoryServiceImpl extends ServiceImpl<OrderStateHistoryMapper, OrderStateHistory> implements OrderStateHistoryService {
    @Override
    public boolean saveOrderState(Order order, Integer userId) {
        OrderStateHistory orderStateHistory = new OrderStateHistory();
        orderStateHistory.setOrderId(order.getId());
        orderStateHistory.setState(order.getState());
        orderStateHistory.setModifier(userId);
        orderStateHistory.setCreateTime(LocalDateTime.now());
        orderStateHistory.setDeleted(false);
        return super.save(orderStateHistory);
    }
}
