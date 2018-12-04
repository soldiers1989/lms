package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.CartMapper;
import com.yniot.lms.db.entity.Cart;
import com.yniot.lms.service.CartService;
import org.springframework.stereotype.Service;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 14:00
 **/
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
}
