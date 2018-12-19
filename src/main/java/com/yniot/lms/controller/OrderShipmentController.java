package com.yniot.lms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.annotation.MailmanOnly;
import com.yniot.lms.annotation.UserOnly;
import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderGoods;
import com.yniot.lms.db.entity.OrderShipment;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.CellService;
import com.yniot.lms.service.OrderGoodsService;
import com.yniot.lms.service.OrderService;
import com.yniot.lms.service.OrderShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    CellService cellService;

    @RequestMapping("/getByOrderId")
    public String getByOrderId(Integer orderId) {
        return getSuccessResult(orderShipmentService.getById(orderId));
    }


    @UserOnly
    @RequestMapping("/user/put")
    public String storageByUser(@RequestParam(name = "orderId") int orderId) {
        if (!isUser()) {
            return noAuth();
        }
        Order order = orderService.getById(orderId);
        List<OrderGoods> orderGoodsList = orderGoodsService.getByOrderId(orderId);
        if (order.getState() == OrderStateEnum.COMMITTED.getState() && orderGoodsList != null && !orderGoodsList.isEmpty()) {
            //锁定格子
            cellService.usedCell(orderGoodsList.get(0).getStorageCellId(), orderId);
            //更新徐柳状态
            return getSuccessResult(orderShipmentService.updateState(orderId, OrderStateEnum.PUT_USER.getState()));
        } else {
            return wrongState();
        }
    }

    @MailmanOnly
    @RequestMapping("/mailman/take")
    public String tookByMailman(@RequestParam(name = "cellId") int cellId) {
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        orderGoodsQueryWrapper.eq("storage_cell_id", cellId);
        List<OrderGoods> orderGoodsList = orderGoodsService.list(orderGoodsQueryWrapper);
        if (orderGoodsList == null || orderGoodsList.isEmpty()) {
            return super.getErrorMsg("没有数据!");
        }
        for (OrderGoods orderGoods : orderGoodsList) {
            if (orderGoods.getState() == OrderStateEnum.PUT_USER.getState()) {
                orderGoods.setState(OrderStateEnum.TOOK_MAILMAN.getState());
            }
        }
        OrderGoods orderGoods = orderGoodsList.get(0);
        int orderId = orderGoods.getOrderId();
        OrderShipment orderShipment = orderShipmentService.getById(orderId);
        if (orderShipment.getState() == OrderStateEnum.PUT_USER.getState()) {
            //更新物流信息
            orderShipmentService.updateState(orderId, OrderStateEnum.PUT_USER.getState());
            //释放格子
            cellService.releaseCellByCellId(orderId);
        }
        return super.getErrorMsg("未知错误");
    }
}

