package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.OrderShipmentMapper;
import com.yniot.lms.db.entity.OrderGoods;
import com.yniot.lms.db.entity.OrderShipment;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.OrderGoodsService;
import com.yniot.lms.service.OrderService;
import com.yniot.lms.service.OrderShipmentService;
import com.yniot.lms.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        return baseMapper.updateState(orderId, state) > 0 && orderService.updateState(orderId, state);
    }

    @Override
    public boolean create(int orderId, int wardrobeId, int userId) {

        return this.create(orderId, wardrobeId, userId, null, null);
    }

    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    OrderService orderService;

    @Override
    public boolean create(int orderId, int wardrobeId, int userId, String address, String phone) {
        List<OrderGoods> orderGoodsList = orderGoodsService.getByOrderId(orderId);
        if (orderGoodsList == null || orderGoodsList.isEmpty()) {
            return false;
        }
        OrderShipment orderShipment = new OrderShipment();
        LocalDateTime now = LocalDateTime.now();
        orderShipment.setId(orderId);
        orderShipment.setState(OrderStateEnum.WAITING_TO_PUT.getState());
        orderShipment.setModifyTime(now);
        orderShipment.setWardrobeId(wardrobeId);
        orderShipment.setModifier(userId);
        orderShipment.setAddress(address);
        orderShipment.setPhone(phone);
        orderShipment.setPassword(Integer.valueOf(CommonUtil.String.getRandomNum(6)));
        orderShipment.setPswExpireTime(now);
        orderShipment.setCreateTime(now);
        orderShipment.setCellId(orderGoodsList.get(0).getStorageCellId());
        return save(orderShipment) && orderService.updateState(orderId, OrderStateEnum.WAITING_TO_PUT.getState());
    }

    @Override
    public boolean checkPassword(int wardrobeId, int cellId, String password) {
        return baseMapper.checkPassword(wardrobeId, cellId, password) > 0;
    }


    @Override
    public void updatePassword() {
        baseMapper.updatePassword(PSW_EXPIRE_MIN);
    }
}
