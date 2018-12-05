package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.dao.OrderGoodsMapper;
import com.yniot.lms.db.entity.*;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.CellService;
import com.yniot.lms.service.OrderCostService;
import com.yniot.lms.service.OrderGoodsService;
import com.yniot.lms.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 11:16
 **/
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements OrderGoodsService {
    protected static Logger logger = Logger.getLogger(OrderGoodsServiceImpl.class);


    @Override
    public boolean finishOrder(Integer orderId) {
        return updateState(orderId, OrderStateEnum.FINISHED.getState());
    }

    @Override
    public boolean cancelOrder(Integer orderId) {
        return baseMapper.cancelOrder(orderId) > 0;
    }

    @Override
    public boolean updateState(Integer orderId, int state) {
        return baseMapper.updateState(orderId, state) > 0;
    }

    @Override
    public List<OrderGoods> getByOrderId(Integer orderId) {
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        orderGoodsQueryWrapper.eq("order_id", orderId);
        return super.list(orderGoodsQueryWrapper);
    }

    @Autowired
    CellService cellService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderCostService orderCostService;

    @Override
    @Transactional
    public boolean save(List<Cart> cartList, int wardrobeId, int orderId) {
        boolean result = false;
        int cellId = cellService.getAvailableCellId(wardrobeId);
        //无可用格子
        if (cellId < 0) {
            return result;
        }
        BigDecimal estCost = new BigDecimal(0);
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        for (Cart cart : cartList) {
            int count = cart.getCount();
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setGoodsId(cart.getGoodsId());
            orderGoods.setOrderId(orderId);
            orderGoods.setStorageCellId(cellId);
            orderGoods.setCreateTime(LocalDateTime.now());
            orderGoods.setWardrobeId(wardrobeId);
            orderGoods.setDeleted(false);
            orderGoods.setState(OrderStateEnum.COMMITTED.getState());
            orderGoods.setCount(count);
            orderGoods.setExtCost(new BigDecimal(0));
            orderGoods.setActUnitPrice(cart.getPrice());
            orderGoods.setImgUrl(cart.getImgUrl());
            orderGoods.setName(cart.getName());
            BigDecimal cost = cart.getPrice().multiply(new BigDecimal(count));
            orderGoods.setEstTotalCost(cost);
            orderGoods.setActTotalCost(cost);
            orderGoods.setEstUnitPrice(cart.getPrice());
            orderGoodsList.add(orderGoods);
            estCost = estCost.add(cost);
        }
        result = saveBatch(orderGoodsList);
        result = result && cellService.usedCell(cellId, orderId);
        result = result && orderCostService.save(orderId, estCost);
        return result;
    }

    @Override
    public boolean removeByOrderId(int orderId) {
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        orderGoodsQueryWrapper.eq("order_id", orderId);
        return remove(orderGoodsQueryWrapper);
    }

    @Override
    public List<OrderGoods> getByOrderIdList(List<Integer> orderIdList) {
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        orderGoodsQueryWrapper.in("order_id", orderIdList);
        return list(orderGoodsQueryWrapper);
    }
}
