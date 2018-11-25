package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.OrderStateHistoryMapper;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderStateHistory;
import com.yniot.lms.service.OrderStateHistoryService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 13:46
 **/
@Service
public class OrderStateHistoryServiceImpl extends ServiceImpl<OrderStateHistoryMapper, OrderStateHistory> implements OrderStateHistoryService {
    @Override
    public boolean saveOrderState(Order order, Integer userId) {
        OrderStateHistory orderStateHistory = new OrderStateHistory();
        orderStateHistory.setOrderId(order.getId());
        orderStateHistory.setState(order.getState());
        orderStateHistory.setModifier(userId);
        orderStateHistory.setCreateTime(new Date());
        orderStateHistory.setDeleted(false);
        return super.save(orderStateHistory);
    }
}
