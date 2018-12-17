package com.yniot.lms.controller;


import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.OrderCostService;
import com.yniot.lms.service.OrderService;
import com.yniot.lms.service.WeChatService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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
    @Autowired
    WeChatService weChatService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/getByOrderId")
    public String getByOrderId(Integer orderId) {
        return getSuccessResult(orderCostService.getById(orderId));
    }


    @RequestMapping("/commitPrice")
    public String commitPrice(String orderCode, Integer orderId, BigDecimal totalPrice) throws WxErrorException {
        boolean result = orderCostService.commitPrice(orderId, totalPrice);
        if (result) {
            orderService.updateState(orderId, OrderStateEnum.WAITING_TO_PAY.getState());
            weChatService.sendNeedPayNotice(orderCode);
        }
        return getSuccessResult(result);
    }


    @RequestMapping("/sendNeedPayNotice")
    public String commitPrice(String orderCode) throws WxErrorException {
        weChatService.sendNeedPayNotice(orderCode);
        return getSuccessResult(1);
    }
}

