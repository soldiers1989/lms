package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.OrderGoodsMapper;
import com.yniot.lms.db.entity.OrderGoods;
import com.yniot.lms.enums.OrderStateEnum;
import com.yniot.lms.service.OrderGoodsService;
import net.sf.ehcache.search.expression.Or;
import org.springframework.stereotype.Service;

import java.util.List;

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
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        List<OrderGoods> orderGoodsList = list(orderGoodsQueryWrapper);
        for (OrderGoods orderGoods : orderGoodsList) {
            orderGoods.setState(OrderStateEnum.FINISHED.getState());
        }
        return saveOrUpdateBatch(orderGoodsList);
    }

    @Override
    public boolean cancelOrder(Integer orderId) {
        QueryWrapper<OrderGoods> orderGoodsQueryWrapper = new QueryWrapper<>();
        List<OrderGoods> orderGoodsList = list(orderGoodsQueryWrapper);
        for (OrderGoods orderGoods : orderGoodsList) {
            orderGoods.setCanceled(true);
        }
        return saveOrUpdateBatch(orderGoodsList);
    }
}
