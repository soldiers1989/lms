package com.yniot.lms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Order;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 13:52
 **/
public interface OrderService extends IService<Order> {

    // 状态：0.创建订单  10.提交订单、待接单 15.已接单.待入柜 20.已接单、待取货
    // 30.已取货、待到店  40.已到店、待确认金额 50.已确认金额并付款、待清洁
    // 60.清洁中 70.完成清洁、待送回  80.已送出、待放回
    // 90.已放回、待取回  100.已取回、待评价（完成订单）
    // 110.评价
    int markExpiredOrder();

    int EXPIRED_IN_MIN = 10;

    Order getByOrderCode(String orderNo);

    boolean expiredOrder(int orderId);

    boolean updateState(int orderId, int state);

    boolean generateOrder(int userId, String openId, int wardrobeId, String description) throws WxErrorException;

    boolean removeByCode(String orderCode);

    boolean receiveOrder(int userId, int orderId);

    IPage<Order> selectByLaundryId(int pageNum, int pageSize, int laundryId, String keyWord, List<Integer> stateList);

}
