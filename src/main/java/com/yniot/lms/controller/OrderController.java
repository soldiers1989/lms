package com.yniot.lms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lane
 * @Date: 2018/11/19 08:50
 * @Description:
 * @Version 1.0.0
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    //1.下单
    @RequestMapping("/select")
    public String getOrder(@RequestParam(name = "orderId") String orderId) {
        return "";
    }
    //2.获取订单

    //3.接单

    //4.

    //5.付款


    //6.获得价格表
}
