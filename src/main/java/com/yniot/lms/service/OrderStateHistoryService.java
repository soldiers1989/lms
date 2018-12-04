package com.yniot.lms.service;

import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderStateHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
public interface OrderStateHistoryService extends IService<OrderStateHistory> {
    boolean saveOrderState(Order order, Integer userId);

}
