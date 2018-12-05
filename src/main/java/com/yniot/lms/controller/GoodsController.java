package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.service.OrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lane
 * @Date: 2018/11/19 08:51
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/orderGoods")
public class GoodsController extends BaseController {
    @Autowired
    OrderGoodsService orderGoodsService;

    @RequestMapping("/getByOrderId")
    public String getByOrderId(Integer orderId) {
        return getSuccessResult(orderGoodsService.getByOrderId(orderId));
    }

}
