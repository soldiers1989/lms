package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderStateHistory;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 13:45
 **/
public interface OrderStateHistoryService extends IService<OrderStateHistory> {
    /**
     * @Author wanggl
     * @Description 保存订单状态
     * @create 14:29 2018-11-25
     * @modify 14:29 2018-11-25
     * @Param [order, userId]
     * @return boolean
     **/
    boolean saveOrderState(Order order,Integer userId);
}
