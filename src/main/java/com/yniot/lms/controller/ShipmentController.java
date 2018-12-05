package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.service.OrderGoodsService;
import com.yniot.lms.service.OrderShipmentService;
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
public class ShipmentController extends BaseController {
    @Autowired
    OrderShipmentService orderShipmentService;

    @RequestMapping("/getByOrderId")
    public String getByOrderId(Integer orderId) {
        return getSuccessResult(orderShipmentService.getById(orderId));
    }

}
