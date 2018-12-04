package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.CartMapper;
import com.yniot.lms.db.entity.Cart;
import com.yniot.lms.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 14:00
 **/
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Override
    public boolean cleanMyCart(int userId) {
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id", userId);
        return super.remove(cartQueryWrapper);
    }

    @Override
    public List<Cart> getMyCart(int userId) {

        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id", userId);

        return super.list(cartQueryWrapper);
    }
}
