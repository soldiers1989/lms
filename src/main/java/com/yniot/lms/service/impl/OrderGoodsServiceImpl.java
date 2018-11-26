package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.OrderGoodsMapper;
import com.yniot.lms.db.entity.OrderGoods;
import com.yniot.lms.service.OrderGoodsService;
import org.springframework.stereotype.Service;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 11:16
 **/
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements OrderGoodsService {


    @Override
    public boolean finishOrder(Integer orderId) {
        return false;
    }

    @Override
    public boolean cancelOrder(Integer orderId) {
        return false;
    }
}
