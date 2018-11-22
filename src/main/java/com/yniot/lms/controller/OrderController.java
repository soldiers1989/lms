package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Auther: lane
 * @Date: 2018/11/19 08:50
 * @Description:
 * @Version 1.0.0
 */
@RequestMapping("/order")
@RestController
public class OrderController extends BaseControllerT<Order> {
    @Autowired
    OrderService orderService;

    //1.下单


    //2.获取订单

    //5.付款


    //获取单个订单
    @RequestMapping("/selectById")
    public String getOrder(@RequestParam(name = "orderId") String orderId) {
        return super.getSuccessResult(orderService.getById(orderId));
    }
    //2.获取订单

    //3.接单
    // 状态：0.创建订单  10.提交订单、待接单 15.已接单.待入柜 20.已接单、待取货
    // 30.已取货、待到店  40.已到店、待确认金额 50.已确认金额并付款、待清洁
    // 60.清洁中 70.完成清洁、待送回  80.已送出、待放回
    // 90.已放回、待取回  100.已取回、待评价
    // 110.已评价（完成订单）
    @RequestMapping("/receive")
    public String receiveOrder(@RequestParam(name = "laundryId") int laundryId,
                               @RequestParam(name = "orderId") int orderId) {
        Order order = orderService.getById(orderId);
        if (order.getLaundryId() == laundryId) {
            order.setAccepted(true);
            return super.getSuccessResult("");

        } else {
            return super.getErrorMsg("没有权限!");
        }
    }

    //4.拒绝订单
    @RequestMapping("/reject")
    public String rejectOrder(@RequestParam(name = "laundryId") int laundryId,
                              @RequestParam(name = "orderId") int orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        Order order = orderService.getOne(orderQueryWrapper);
        return super.getSuccessResult("");
    }

    //7.评价
    @RequestMapping("/comment")
    public String logout(@RequestParam(name = "userId") int userId,
                         @RequestParam(name = "orderId") int orderId,
                         @RequestParam(name = "stars") int stars,
                         @RequestParam(name = "comment") String comment) {
        Order order = orderService.getById(orderId);
        if (userId == order.getUserId()) {
            order.setComment(comment);
            order.setStars(stars);
            order.setCommitTime(new Date());
            return super.getSuccessResult(orderService.saveOrUpdate(order));
        } else {
            return super.getErrorMsg("没有权限!");
        }
    }
}
