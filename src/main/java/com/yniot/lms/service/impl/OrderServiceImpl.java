package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.OrderMapper;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.User;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    //7.检查订单编号
}
