package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.OrderShipment;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 11:15
 **/
public interface OrderShipmentService extends IService<OrderShipment> {
    int PSW_EXPIRE_MIN = 30;

    boolean updateState(int orderId, int state);

    boolean create(int orderId, int wardrobeId, int userId);

    void updatePassword();
}
