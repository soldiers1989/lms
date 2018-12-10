package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.*;
import com.yniot.lms.controller.PageVo.OrderPVO;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderComment;
import com.yniot.lms.db.entity.OrderGoods;
import com.yniot.lms.db.entity.OrderShipment;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.*;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    OrderStateHistoryService orderStateHistoryService;
    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    OrderCommentService orderCommentService;
    @Autowired
    OrderShipmentService orderShipmentService;
    @Autowired
    CellService cellService;

    //1.提交订单
    @RequestMapping("/commit")
    public String createOrder(int wardrobeId) throws WxErrorException {
        //获取用户
        return super.getSuccessResult(orderService.generateOrder(getId(), getOpenId(), wardrobeId));
    }


    //5.付款

    //3.接单  现在设置成默认接单
    @LaundryOnly
    @RequestMapping("/accept")
    public String receiveOrder(@RequestParam(name = "orderId") int orderId
    ) {
        if (!isLaundry()) {
            return super.noAuth();
        } else {
            return getSuccessResult(orderService.receiveOrder(getId(), orderId));
        }
    }

    //3.接单  现在设置成默认接单
    @LaundryOnly
    @RequestMapping("/acceptBatch")
    public String receiveOrderBatch(@RequestParam(name = "orderIdList[]") List<Integer> orderIdList) {
        if (!isLaundry()) {
            return super.noAuth();
        } else {
            int cnt = 0;
            for (Integer orderId : orderIdList) {
                cnt += orderService.receiveOrder(getId(), orderId) ? 1 : 0;
            }
            return getSuccessResult(cnt);
        }
    }

    //4.拒绝订单
    @RequestMapping("/cancel")
    public String cancelOrderUser(@RequestParam(name = "orderId") int orderId) {
        Order order = orderService.getById(orderId);
        if (order.getState() == OrderStateEnum.COMMITTED.getState()) {
            return super.wrongState();
        }
        if (!super.isUser() && !super.isLaundry()) {
            return super.noAuth();
        }
        order.setState(OrderStateEnum.CANCELED.getState());
        order.setCanceledTime(LocalDateTime.now());
        //这里是用户id,没有使用洗衣店id
        order.setCanceledBy(super.getUser().getId());
        order.setState(OrderStateEnum.CANCELED.getState());
        return super.getSuccessResult(orderService.saveOrUpdate(order) && orderStateHistoryService.saveOrderState(order, super.getUser().getId()));
    }


    //完成订单.
    @Unfinished
    @RequestMapping("/finish")
    public String finishOrder(@RequestParam(name = "orderId") int orderId) {
        Order order = orderService.getById(orderId);
        OrderShipment orderShipment = orderShipmentService.getById(orderId);
        if (orderShipment.getState() != OrderStateEnum.TOOK_USER.getState()) {
            return super.wrongState();
        }
        if (!isUser()) {
            return noAuth();
        }
        order.setState(OrderStateEnum.FINISHED.getState());
        order.setFinishedTime(LocalDateTime.now());
        return super.getSuccessResult(orderService.saveOrUpdate(order) && orderStateHistoryService.saveOrderState(order, getUser().getId()));
    }


    //7.评价
    @UserOnly
    @RequestMapping("/comment")
    public String logout(
            @RequestParam(name = "orderId") int orderId,
            @RequestParam(name = "stars") int stars,
            @RequestParam(name = "comment") String comment) {
        Order order = orderService.getById(orderId);
        if (order.getState() != OrderStateEnum.FINISHED.getState()) {
            return super.wrongState();
        }
        OrderComment orderComment = new OrderComment();
        if (order != null && super.getUser().getId() == order.getUserId()) {
            orderComment.setId(orderId);
            orderComment.setContent(comment);
            orderComment.setStars(stars);
            orderComment.setCreateTime(new Date());
            orderComment.setUsername(getUser().getUsername());
            orderComment.setUserId(getUser().getId());
            orderComment.setLaundryId(order.getLaundryId());
            return super.getSuccessResult(orderCommentService.save(orderComment));
        } else {
            return super.noAuth();
        }
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
        return super.getErrorMsg("");
    }


    //获取单个订单
    @Finished
    @LoginOnly
    @RequestMapping("/selectById")
    public String getOrder(Integer orderId) {
        return super.getSuccessResult(orderService.getById(orderId));
    }


    @LoginOnly
    @RequestMapping("/selectByCode")
    public String getOrder(@RequestParam(name = "orderCode") String orderCode) {
        return super.getSuccessResult(orderService.getByOrderCode(orderCode));
    }

    //2.获取订单
    @Unfinished
    @AdminOnly
    @RequestMapping("/select")
    public String select(@RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
                         @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "20") int pageSize,
                         @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            orderQueryWrapper.like("code", keyWord)
                    .or().like("description", keyWord);
        }
        return super.getSuccessPage(orderService.page(new Page(pageNum, pageSize), orderQueryWrapper));
    }

    //2.客户获取订单
    @Unfinished
    @UserOnly
    @RequestMapping("/selectByUserId")
    public String selectByUserId(@RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
                                 @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "20") int pageSize,
                                 @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id", getId());
        orderQueryWrapper.orderByDesc("create_time");
        if (StringUtils.isNotEmpty(keyWord)) {
            orderQueryWrapper.like("code", keyWord)
                    .or().like("description", keyWord);
        }
        IPage<Order> orderIPage = orderService.page(new Page(pageNum, pageSize), orderQueryWrapper);
        List<Order> orderList = orderIPage.getRecords();
        List<Integer> orderIdList = orderList.stream().map(Order::getId).collect(Collectors.toList());
        List<OrderGoods> orderGoodsList = orderGoodsService.getByOrderIdList(orderIdList);
        List<OrderPVO> orderPVOList = OrderPVO.combine(orderList, orderGoodsList);
        return super.getSuccessResult(orderPVOList, orderIPage.getCurrent(), orderIPage.getSize(), orderIPage.getTotal());
    }


    /**
     * <el-menu-item index="1">待接单</el-menu-item>
     * <el-menu-item index="2">待存放</el-menu-item>
     * <el-menu-item index="3">待提货</el-menu-item>
     * <el-menu-item index="4">已提货</el-menu-item>
     * <el-menu-item index="5">待付款</el-menu-item>
     * <el-menu-item index="6">待清洁</el-menu-item>
     * <el-menu-item index="7">待送出</el-menu-item>
     * <el-menu-item index="8">已失效</el-menu-item>
     *
     * @return java.lang.String
     * @Author wanggl(lane)
     * @Description //TODO
     * @Date 13:45 2018-12-10
     * @Param [keyWord, pageSize, pageNum]
     **/
    @LaundryOnly
    @RequestMapping("/selectByLaundryId")
    public String selectByLaundryId(@RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
                                    @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "20") int pageSize,
                                    @RequestParam(name = "state", required = false, defaultValue = "20") int state,
                                    @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        if (!isLaundry()) {
            return noAuth();
        }


        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("laundry_id", getLaundryId());
        if (StringUtils.isNotEmpty(keyWord)) {
            orderQueryWrapper.like("code", keyWord)
                    .or().like("description", keyWord);
        }
        return super.getSuccessPage(orderService.page(new Page(pageNum, pageSize), orderQueryWrapper));
    }
}
