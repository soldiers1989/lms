package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.OrderMapper;
import com.yniot.lms.db.entity.Cart;
import com.yniot.lms.db.entity.Laundry;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.*;
import com.yniot.lms.utils.CommonUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 14:00
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    OrderStateHistoryService orderStateHistoryService;
    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    CartService cartService;
    @Autowired
    LaundryService laundryService;
    @Autowired
    WeChatService weChatService;
    @Autowired
    CellService cellService;
    @Autowired
    OrderCostService orderCostService;
    @Autowired
    OrderShipmentService orderShipmentService;

    @Override
    public int markExpiredOrder() {
        return baseMapper.markExpiredOrder(OrderStateEnum.CANCELED_TIMEOUT.getState());
    }

    @Override
    public Order getByOrderCode(String orderCode) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("code", orderCode);
        return getOne(orderQueryWrapper);
    }

    @Override
    public boolean expiredOrder(int orderId) {
        //设置货物无效
        orderGoodsService.cancelOrder(orderId);
        //格子设置为可用,释放格子
        cellService.releaseCellByOrderId(orderId);

        return baseMapper.updateState(orderId, OrderStateEnum.CANCELED.getState()) > 0;
    }

    @Override
    public boolean removeByCode(String orderCode) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("code", orderCode);
        return remove(orderQueryWrapper);
    }


    @Override
    public boolean updateState(int orderId, int state) {
        return baseMapper.updateState(orderId, state) > 0;
    }

    @Override
    @Transactional
    public boolean generateOrder(int userId, String openId, int wardrobeId) throws WxErrorException {
        if (cellService.getAvailableCellId(wardrobeId) < 0) {
            return false;
        }
        Laundry laundry = laundryService.getByWardrobeId(wardrobeId);
        int laundryId = laundry.getId();
        List<Cart> cartList = cartService.getMyCart(userId);
        String orderCode = null;
        //0.检查订单编号是否重复,与订单id无关,高并发下极小概率会重复
        for (int i = 0; i < 20; i++) {
            orderCode = getOrderCode();
            Order temp = getByOrderCode(orderCode);
            if (temp == null) {
                break;
            }
        }
        Order order = new Order();
        int totalCount = 0;
        boolean result = false;
        if (StringUtils.isNotEmpty(orderCode)) {
            LocalDateTime now = LocalDateTime.now();
            order.setCode(orderCode);
            order.setUserId(userId);
            order.setState(OrderStateEnum.COMMITTED.getState());
            order.setCommitTime(now);
            order.setLaundryId(laundryId);
            order.setExpiredTime(CommonUtil.Date.plusSecond(now, OrderService.EXPIRED_IN_MIN * 60));
            order.setCreateTime(now);
            order.setUserOpenId(openId);
            order.setWardrobeId(wardrobeId);
            order.setLaundryOpenId(laundry.getOpenId());
            for (Cart cart : cartList) {
                int count = cart.getCount();
                totalCount += count;
            }
            order.setCount(totalCount);
            result = save(order);
        }
        if (result) {
            order = getByOrderCode(orderCode);
            //保存到订单-商品表
            result = orderGoodsService.save(cartList, wardrobeId, order.getId());
        }
        if (result) {
            //清空购物车
            cartService.cleanMyCart(userId);
        }
        if (result) {
            //发送提示信息到商家微信和PC端
            weChatService.sendOrderGenerateNotice(order.getCode());
            //保存订单状态
            orderStateHistoryService.saveOrderState(order.getId(), OrderStateEnum.COMMITTED.getState(), userId);
        }
        return result;
    }

    //6.获取唯一的订单编号
    private static synchronized String getOrderCode() {
        String randomWord = CommonUtil.String.RandomWord(4);
        String dateStr = CommonUtil.Date.getNowDate("YYYYMMddHHmmssSSS");
        return "LO" + dateStr + randomWord.toUpperCase();
    }

    @Override
    public boolean receiveOrder(int userId, int orderId) {
        Order order = getById(orderId);
        //订单过期
        if (LocalDateTime.now().isAfter(order.getExpiredTime())) {
            //设置订单过期
            expiredOrder(orderId);
            //保存状态
            orderStateHistoryService.saveOrderState(order, userId);
        }
        if (order.getState() == OrderStateEnum.COMMITTED.getState()) {
            //更改为接单状态
            updateState(orderId, OrderStateEnum.ACCEPTED.getState());
            //更新物流信息
            orderShipmentService.create(order.getId(), order.getWardrobeId(), userId);
            //保存状态信息
            return orderStateHistoryService.saveOrderState(orderId, OrderStateEnum.ACCEPTED.getState(), userId);
        }
        return false;
    }


    public IPage<Order> selectByLaundryId(int pageNum, int pageSize, int laundryId, String keyWord, int state) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("laundry_id", laundryId).eq("state", state);
        if (StringUtils.isNotEmpty(keyWord)) {
            orderQueryWrapper.like("code", keyWord)
                    .or().like("description", keyWord);
        }
        orderQueryWrapper.orderByDesc("commit_time");
        return page(new Page(pageNum, pageSize), orderQueryWrapper);
    }
}
