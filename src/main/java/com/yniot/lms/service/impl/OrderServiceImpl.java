package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.OrderMapper;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.OrderService;
import com.yniot.lms.service.OrderStateHistoryService;
import com.yniot.lms.utils.CommonUtil;
import net.sf.ehcache.search.expression.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 14:00
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    OrderStateHistoryService orderStateHistoryService;

    @Override
    public int markExpiredOrder() {
        return baseMapper.markExpiredOrder();
    }

    @Override
    public Order getByOrderCode(String orderCode) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("code", orderCode);
        return getOne(orderQueryWrapper);
    }

    @Override
    public boolean expiredOrder(int orderId) {
        Order order = getById(orderId);
        order.setExpired(true);
        return saveOrUpdate(order);
    }

}
