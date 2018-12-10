package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Cart;
import com.yniot.lms.db.entity.OrderGoods;

import java.math.BigDecimal;
import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 11:15
 **/
public interface OrderGoodsService extends IService<OrderGoods> {
    boolean finishOrder(Integer orderId);

    boolean cancelOrder(Integer orderId);

    boolean updateState(Integer orderId, int state);

    List<OrderGoods> getByOrderId(Integer orderId);

    boolean save(List<Cart> cartList,  int wardrobeId, int orderId);

    boolean removeByOrderId(int orderId);

    List<OrderGoods> getByOrderIdList(List<Integer> orderIdList);
}
