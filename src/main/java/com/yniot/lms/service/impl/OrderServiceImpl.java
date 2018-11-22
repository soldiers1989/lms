package com.yniot.lms.service.impl;

import com.yniot.lms.service.OrderService;
import com.yniot.lms.utils.CommonUtil;
import org.springframework.stereotype.Service;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 14:00
 **/
@Service
public class OrderServiceImpl implements OrderService {

    // 状态：0.创建订单  10.提交订单、未入柜 20.已入柜、待取货
    // 30.已取货、待到店  40.已到店、待确认金额 50.已确认金额并付款、待清洁
    // 60.清洁中 70.完成清洁、待送回  80.已送出、待放回
    // 90.已放回、待取回  100.已取回、待评价
    // 110.已评价（完成订单）

    //1.下单


    //2.获取订单

    //3.接单

    //4.

    //5.付款


    //6.获取唯一的订单编号
    static synchronized String getOrderCode() {
        String randomWord = CommonUtil.String.RandomWord(4);
        String dateStr = CommonUtil.Date.getNowDate("YYYYMMddHHmmssSSS");
        return "LO"+dateStr + randomWord;
    }


    //7.检查订单编号
}
