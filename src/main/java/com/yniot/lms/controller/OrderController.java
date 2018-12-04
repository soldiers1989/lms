package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.jmx.snmp.Timestamp;
import com.yniot.lms.annotation.*;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.*;
import com.yniot.lms.enums.ShipmentEnum;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.*;
import com.yniot.lms.utils.CommonUtil;
import net.sf.ehcache.search.expression.Or;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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


    //1.提交订单
    @RequestMapping("/commit")
    public String createOrder(@RequestParam(name = "order") Order order,
                              @RequestParam(name = "orderGoodsList[]") List<OrderGoods> orderGoodsList) {
        //获取用户
        User user = super.getUser();
        boolean result1 = false;
        boolean result2 = false;


        //0.检查订单编号是否重复,与订单id无关,高并发下小概率会重复
        String orderCode = null;
        for (int i = 0; i < 20; i++) {
            orderCode = getOrderCode();
            QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
            orderQueryWrapper.eq("code", orderCode);
            Order temp = orderService.getOne(orderQueryWrapper);
            if (temp == null) {
                break;
            }
        }
        //1.如果订单编号为空则终止
        if (StringUtils.isNotEmpty(orderCode)) {
            Date now = new Date();
            Date expiredTime = new Date(now.getTime() - OrderService.EXPIRED_IN_MIN * 60000);
            order.setCode(orderCode);
            order.setUserId(user.getId());
            order.setState(OrderStateEnum.COMMITTED.getState());
            order.setCommitTime(now);
            order.setExpired(false);
            order.setExpiredTime(expiredTime);
            order.setCreateTime(now);
            result1 = orderService.save(order);
            //这里还需要获得订单id
        }
        //2.插入 orderGoods
        if (result1) {
            QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
            orderQueryWrapper.eq("code", orderCode);
            order = orderService.getOne(orderQueryWrapper);
            if (order != null) {
                for (OrderGoods orderGoods : orderGoodsList) {
                    orderGoods.setOrderId(order.getId());
                    orderGoods.setCreateTime(new Date());
                    orderGoods.setDeleted(false);
                }
                result2 = orderGoodsService.saveBatch(orderGoodsList);
            }
        }
        //3.如果商品插入失败,则订单也需要删除
        if (result2) {
            QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
            orderQueryWrapper.eq("code", orderCode);
            orderService.remove(orderQueryWrapper);
        } else {
            //保存订单状态
            orderStateHistoryService.saveOrderState(order, super.getUser().getId());
        }
        boolean re = result1 && result2;
        if (re) {
            //4.发送提示信息到商家微信和PC端


        }
            //5.生成二维码

        return super.getSuccessResult(re);
    }





    //5.付款

    //3.接单
    @LaundryOnly
    @RequestMapping("/accept")
    public String receiveOrder(@RequestParam(name = "orderId") int orderId) {
        Order order = orderService.getById(orderId);
        if (!isLaundry()) {
            return super.noAuth();
        }
        Date commitTime = order.getCommitTime();
        Timestamp commitTimestamp = new Timestamp(commitTime.getTime());
        Timestamp nowTimestamp = new Timestamp(new Date().getTime());
        int expireMin = order.getExpireInMin();
        //是否已经过期
        if (nowTimestamp.getDateTime() - commitTimestamp.getDateTime() >= expireMin * 60000) {
            return super.expired();
        }
        if (!(order.getState() == OrderStateEnum.COMMITTED.getState())) {
            return super.wrongState();
        }
        //更改状态
        order.setAccepted(true);
        order.setAcceptedTime(new Date());
        order.setState(OrderStateEnum.ACCEPTED.getState());
        return super.getSuccessResult(orderService.saveOrUpdate(order) && orderStateHistoryService.saveOrderState(order, super.getUser().getId()));
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
        order.setCanceled(true);
        order.setCanceledTime(new Date());
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
        if (orderShipment.getState() != ShipmentEnum.TOOK_USER.getState()) {
            return super.wrongState();
        }
        if (!isUser()) {
            return noAuth();
        }
        order.setState(OrderStateEnum.FINISHED.getState());
        order.setFinishedTime(new Date());
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
        if (order.getState() != OrderStateEnum.COMMITTED.getState()) {
            return wrongState();
        }
        //更改物流状态
        OrderShipment orderShipment = orderShipmentService.getById(orderId);
        orderShipment.setState(ShipmentEnum.PUT_USER.getState());
        orderShipment.setModifyTime(new Date());
        orderShipmentService.saveOrUpdate(orderShipment);
        //更改每个物品的状态
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        List<OrderGoods> orderGoodsList = orderGoodsService.list(orderGoodsQueryWrapper);
        for (OrderGoods orderGoods : orderGoodsList) {
            orderGoods.setState(ShipmentEnum.PUT_USER.getState());
        }
        orderGoodsService.saveOrUpdateBatch(orderGoodsList);
        return "";
    }

    @MailmanOnly
    @RequestMapping("/mailman/take")
    public String tookByMailman(@RequestParam(name = "cellId") int cellId) {
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        orderGoodsQueryWrapper.eq("storage_cell_id", cellId);
        List<OrderGoods> orderGoodsList = orderGoodsService.list(orderGoodsQueryWrapper);
        if (orderGoodsList.isEmpty()) {
            return super.getErrorMsg("没有数据!");
        }
        for (OrderGoods orderGoods : orderGoodsList) {
            if (orderGoods.getState() == ShipmentEnum.PUT_USER.getState()) {
                orderGoods.setState(ShipmentEnum.TOOK_MAILMAN.getState());
            }
        }
        OrderGoods orderGoods = orderGoodsList.get(0);
        int orderId = orderGoods.getOrderId();
        OrderShipment orderShipment = orderShipmentService.getById(orderId);
        if (orderShipment.getState() == ShipmentEnum.PUT_USER.getState()) {
            orderShipment.setState(ShipmentEnum.TOOK_MAILMAN.getState());
            orderShipmentService.saveOrUpdate(orderShipment);
        }
        return super.getErrorMsg("");
    }


    //6.获取唯一的订单编号
    private static synchronized String getOrderCode() {
        String randomWord = CommonUtil.String.RandomWord(4);
        String dateStr = CommonUtil.Date.getNowDate("YYYYMMddHHmmssSSS");
        return "LO" + dateStr + randomWord;
    }


    //获取单个订单
    @Finished
    @RequestMapping("/selectById")
    public String getOrder(@RequestParam(name = "orderId") int orderId) {
        return super.getSuccessResult(orderService.getById(orderId));
    }

    //2.获取订单
    @Unfinished
    @RequestMapping("/select")
    public String select(@RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
                         @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "") int pageSize,
                         @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "") int pageNum) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderService.page(new Page<>(pageNum, pageSize), orderQueryWrapper);
        return super.getSuccessPage(orderService.page(new Page(pageNum, pageSize), orderQueryWrapper));
    }
}
