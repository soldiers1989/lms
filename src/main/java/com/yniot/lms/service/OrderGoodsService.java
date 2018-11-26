package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.OrderGoods;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 11:15
 **/
public interface OrderGoodsService extends IService<OrderGoods> {
    boolean finishOrder(Integer orderId);
    boolean cancelOrder(Integer orderId);
}
