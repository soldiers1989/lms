package com.yniot.lms.controller.PageVo;

import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderGoods;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: lane
 * @Date: 2018-12-05 10:24
 * @Description:
 * @Version 1.0.0
 */
public class OrderPVO {
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private Order order = null;

    public static List<OrderPVO> combine(List<Order> orderList, List<OrderGoods> orderGoodsList) {
        List<OrderPVO> orderPVOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderPVO orderPVO = new OrderPVO();
            orderPVO.setOrder(order);
            orderPVO.setOrderGoodsList(orderGoodsList.stream().filter((OrderGoods orderGoods) -> orderGoods.getOrderId() == order.getId()).collect(Collectors.toList()));
            orderPVOList.add(orderPVO);
        }
        return orderPVOList;
    }

    public List<OrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    private List<OrderGoods> orderGoodsList = null;
}
