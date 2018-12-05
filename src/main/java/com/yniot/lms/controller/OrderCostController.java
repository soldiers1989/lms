package com.yniot.lms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.db.entity.OrderCost;
import com.yniot.lms.service.OrderCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.yniot.lms.controller.commonController.BaseController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
@RestController
@RequestMapping("/orderCost")
public class OrderCostController extends BaseController {
    @Autowired
    OrderCostService orderCostService;

    @RequestMapping("/getByOrderId")
    public String getByOrderId(Integer orderId) {
        return getSuccessResult(orderCostService.getById(orderId));
    }
}

