package com.yniot.lms.controller;

import com.yniot.lms.service.OrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lane
 * @Date: 2018/11/26 08:49
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    //已存放
    //已取货
    //已放回
    @Autowired
    OrderGoodsService orderGoodsService;


    @RequestMapping("/put")
    public String put1(@RequestParam(name = "") int orderGoodsId) {

        return "";
    }

}
