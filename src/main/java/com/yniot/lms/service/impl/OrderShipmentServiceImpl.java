package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.OrderShipmentMapper;
import com.yniot.lms.db.entity.OrderShipment;
import com.yniot.lms.enums.ShipmentEnum;
import com.yniot.lms.service.OrderShipmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 11:16
 **/
@Service
public class OrderShipmentServiceImpl extends ServiceImpl<OrderShipmentMapper, OrderShipment> implements OrderShipmentService {


    @Override
    public boolean updateState(int orderId, int state) {
        return baseMapper.updateState(orderId, state) > 0;
    }

    @Override
    public boolean create(int orderId, int wardrobeId, int userId) {
        OrderShipment orderShipment = new OrderShipment();
        orderShipment.setId(orderId);
        orderShipment.setState(ShipmentEnum.WAITING.getState());
        orderShipment.setModifyTime(LocalDateTime.now());
        orderShipment.setWardrobeId(wardrobeId);
        orderShipment.setModifier(userId);
        return save(orderShipment);
    }
}
