package com.yniot.lms.controller;


import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.service.OrderShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
@RestController
@RequestMapping("/orderShipment")
public class OrderShipmentController extends BaseController {
    @Autowired
    OrderShipmentService orderShipmentService;

    @RequestMapping("/getByOrderId")
    public String getByOrderId(Integer orderId) {
        return getSuccessResult(orderShipmentService.getById(orderId));
    }
}

