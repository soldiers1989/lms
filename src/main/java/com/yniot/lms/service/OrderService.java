package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.User;

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


}
