package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.jmx.snmp.Timestamp;
import com.yniot.lms.annotation.Finished;
import com.yniot.lms.annotation.LaundryOnly;
import com.yniot.lms.annotation.Unfinished;
import com.yniot.lms.annotation.UserOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderGoods;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.enums.GoodsStateEnum;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.OrderGoodsService;
import com.yniot.lms.service.OrderService;
import com.yniot.lms.service.OrderStateHistoryService;
import com.yniot.lms.utils.CommonUtil;
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
            for (OrderGoods orderGoods : orderGoodsList) {
                orderGoods.setOrderId(order.getId());
                orderGoods.setCreateTime(new Date());
                orderGoods.setDeleted(false);
            }
            result2 = orderGoodsService.saveBatch(orderGoodsList);
        }
        //3.如果商品插入失败,则订单也需要删除
        if (result2) {
            QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
            orderQueryWrapper.eq("code", orderCode);
            Order temp = orderService.getOne(orderQueryWrapper);
            orderService.remove(orderQueryWrapper);
        } else {
            //保存订单状态
            orderStateHistoryService.saveOrderState(order, super.getUser().getId());
        }
        boolean re = result1 && result2;
        if(re){
            //4.发送提示信息到商家微信和PC端
        }


        return super.getSuccessResult(re);
    }

    //5.付款

    //3.接单
    // 状态：0.创建订单  10.提交订单、待接单 15.已接单.待入柜 20.已接单、待取货
    // 30.已取货、待到店  40.已到店、待确认金额 50.已确认金额并付款、待清洁
    // 60.清洁中 70.完成清洁、待送回  80.已送出、待放回
    // 90.已放回、待取回  100.已取回、待评价
    // 110.已评价（完成订单）
    @LaundryOnly
    @RequestMapping("/accept")
    public String receiveOrder(@RequestParam(name = "orderId") int orderId) {
        Order order = orderService.getById(orderId);
        if (!isLaundry()) {
            return super.noAuth();
        }
        Date commitTime = order.getCommentTime();
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
        if (order.getState() != OrderStateEnum.TOOK_USER.getState()) {
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
    @RequestMapping("/comment")
    public String logout(
            @RequestParam(name = "orderId") int orderId,
            @RequestParam(name = "stars") int stars,
            @RequestParam(name = "comment") String comment) {
        Order order = orderService.getById(orderId);
        if (order.getState() != OrderStateEnum.FINISHED.getState()) {
            return super.wrongState();
        }
        if (order != null && super.getUser().getId() == order.getUserId()) {
            order.setComment(comment);
            order.setStars(stars);
            order.setCommitTime(new Date());
            return super.getSuccessResult(orderService.saveOrUpdate(order) && orderStateHistoryService.saveOrderState(order, super.getUser().getId()));
        } else {
            return super.noAuth();
        }
    }


    @UserOnly
    @RequestMapping("/user/storage")
    public String storageByUser(@RequestParam(name = "orderId") int orderId) {
        if (!isUser()) {
            return noAuth();
        }
        Order order = orderService.getById(orderId);
        order.setState(OrderStateEnum.PUT_USER.getState());
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        List<OrderGoods> orderGoodsList = orderGoodsService.list(orderGoodsQueryWrapper);
//        List<Integer> orderGoodsId = orderGoodsList.stream().map(OrderGoods::getId).collect(Collectors.toList());
        for (OrderGoods orderGoods : orderGoodsList) {
            orderGoods.setState(GoodsStateEnum.PUT_USER.getType());
        }
        orderGoodsService.saveOrUpdateBatch(orderGoodsList);
        return "";
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
