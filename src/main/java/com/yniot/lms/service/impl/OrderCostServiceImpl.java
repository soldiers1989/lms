package com.yniot.lms.service.impl;

import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderCost;
import com.yniot.lms.db.dao.OrderCostMapper;
import com.yniot.lms.service.OrderCostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.service.OrderService;
import com.yniot.lms.service.WeChatService;
import com.yniot.lms.utils.CommonUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
@Service
public class OrderCostServiceImpl extends ServiceImpl<OrderCostMapper, OrderCost> implements OrderCostService {
    @Autowired
    WeChatService weChatService;
    @Autowired
    OrderService orderService;

    /**
     * @return boolean
     * @Author wanggl(lane)
     * @Description //TODO 预估价格
     * @Date 15:33 2018-12-17
     * @Param [orderId, estCost]
     **/
    @Override
    public boolean saveEstCost(int orderId, BigDecimal estCost) {
        OrderCost orderCost = new OrderCost();
        orderCost.setId(orderId);
        orderCost.setEstTotalCost(estCost);
        orderCost.setActPaidCost(estCost);
        orderCost.setActTotalCost(estCost);
        orderCost.setConfirmed(false);
        orderCost.setGenerateTime(LocalDateTime.now());
        orderCost.setExtCost(new BigDecimal(0));
        return save(orderCost);
    }


    /**
     * @return boolean
     * @Author wanggl(lane)
     * @Description //TODO 商家调整后的实际价格
     * @Date 15:33 2018-12-17
     * @Param [orderId, actCost]
     **/
    @Override
    public boolean commitPrice(int orderId, BigDecimal actCost) throws WxErrorException {
        OrderCost orderCost = getById(orderId);
        Order order = orderService.getById(orderId);
        orderCost.setActTotalCost(actCost);
        LocalDateTime now = LocalDateTime.now();
        orderCost.setGenerateTime(now);
        orderCost.setExpiredTime(CommonUtil.Date.plusSecond(now, orderCost.getExpiredInMin() * 60));
        return saveOrUpdate(orderCost);
    }

    @Override
    public boolean paid(int orderId, BigDecimal price) {
        return baseMapper.paid(price, orderId) > 0;
    }
}
