package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.Unfinished;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderGoods;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.OrderGoodsService;
import com.yniot.lms.service.OrderService;
import com.yniot.lms.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
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
    OrderGoodsService orderGoodsService;

    //1.提交订单
    @RequestMapping("/create")
    public String createOrder(@RequestParam(name = "order") Order order,
                              @RequestParam(name = "orderGoodsList[]") List<OrderGoods> orderGoodsList) {
        boolean result1 = false;
        boolean result2 = false;
        //0.检查订单编号是否重复,与订单id无关,高并发下很小概率会重复
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
            order.setCode(orderCode);
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
        }
        return super.getSuccessResult(result1 && result2 ? 1 : 0);
    }

    //6.获取唯一的订单编号
    private static synchronized String getOrderCode() {
        String randomWord = CommonUtil.String.RandomWord(4);
        String dateStr = CommonUtil.Date.getNowDate("YYYYMMddHHmmssSSS");
        return "LO" + dateStr + randomWord;
    }


    //2.获取订单

    //5.付款


    //获取单个订单
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


    //3.接单
    // 状态：0.创建订单  10.提交订单、待接单 15.已接单.待入柜 20.已接单、待取货
    // 30.已取货、待到店  40.已到店、待确认金额 50.已确认金额并付款、待清洁
    // 60.清洁中 70.完成清洁、待送回  80.已送出、待放回
    // 90.已放回、待取回  100.已取回、待评价
    // 110.已评价（完成订单）
    @RequestMapping("/receive")
    public String receiveOrder(@RequestParam(name = "laundryId") int laundryId,
                               @RequestParam(name = "orderId") int orderId) {
        Order order = orderService.getById(orderId);
        User user = super.getUser();
        if (user == null) {
            return super.getErrorMsg("!");
        }

        if (order != null && order.getLaundryId() == laundryId) {
            order.setAccepted(true);
            order.setAcceptedTime(new Date());
            return super.getSuccessResult("");

        } else {
            return super.getErrorMsg("没有权限!");
        }
    }

    //4.拒绝订单
    @Unfinished
    @RequestMapping("/reject")
    public String rejectOrder(@RequestParam(name = "laundryId") int laundryId,
                              @RequestParam(name = "orderId") int orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        Order order = orderService.getOne(orderQueryWrapper);
        return super.getSuccessResult("");
    }

    //7.评价
    @RequestMapping("/comment")
    public String logout(@RequestParam(name = "userId") int userId,
                         @RequestParam(name = "orderId") int orderId,
                         @RequestParam(name = "stars") int stars,
                         @RequestParam(name = "comment") String comment) {
        Order order = orderService.getById(orderId);
        if (order != null && userId == order.getUserId()) {
            order.setComment(comment);
            order.setStars(stars);
            order.setCommitTime(new Date());
            return super.getSuccessResult(orderService.saveOrUpdate(order));
        } else {
            return super.getErrorMsg("没有权限!");
        }
    }
}
