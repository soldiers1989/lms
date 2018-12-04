package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Cart;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 13:52
 **/
public interface CartService extends IService<Cart> {

    boolean cleanMyCart(int userId);
}
