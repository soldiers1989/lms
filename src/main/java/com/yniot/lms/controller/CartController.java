package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Cart;
import com.yniot.lms.db.entity.GoodsType;
import com.yniot.lms.service.CartService;
import com.yniot.lms.service.GoodsTypeService;
import com.yniot.lms.service.PriceTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @project: lms
 * @description:
 * @cartor: wanggl
 * @create: 2018-11-22 18:40
 **/
@AdminOnly
@RestController
@RequestMapping("/cart")
public class CartController extends BaseControllerT<Cart> {
    @Autowired
    CartService cartService;
    @Autowired
    GoodsTypeService goodsTypeService;
    @Autowired
    PriceTableService priceTableService;

    @RequestMapping("/add")
    public String createCart(int goodsId) {
        int userId = getId();
        Cart cart = this.getCartByGoodsIdNUserId(userId, goodsId);
        if (cart == null || true) {
            GoodsType goodsType = goodsTypeService.getById(goodsId);
            cart = new Cart();
            cart.setCount(1);
            cart.setGoodsId(goodsId);
            cart.setName(goodsType.getName());
            cart.setCreateTime(new Date());
            cart.setUserId(userId);
            cart.setImgUrl(goodsType.getBannerImgUrl());
            //平均价格
            cart.setPrice(goodsType.getAvgPrice());
            return super.getSuccessResult(cartService.save(cart));
        } else {
            cart.setCount(cart.getCount() + 1);
            return super.getSuccessResult(cartService.saveOrUpdate(cart));
        }
    }

    private Cart getCartByGoodsIdNUserId(int userId, int goodsId) {
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("goods_id", goodsId).eq("user_id", userId);
        return cartService.getOne(cartQueryWrapper);
    }


//    @RequestMapping("/delete")
//    public String deleteCart(int goodsId) {
//        Cart cart = this.getCartByGoodsIdNUserId(getId(), goodsId);
//        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
//        cartQueryWrapper.eq("user_id", getId());
//        if (cart == null) {
//            return wrongState();
//        } else {
//            int left = cart.getCount() - 1;
//            if (left <= 1) {
//                return getSuccessResult(cartService.removeById(cart.getId()));
//            } else {
//                cart.setCount(left);
//                return getSuccessResult(cartService.saveOrUpdate(cart));
//            }
//        }
//    }


    @RequestMapping("/delete")
    public String deleteCart(int cartId) {
        return getSuccessResult(cartService.removeById(cartId));
    }

    @RequestMapping("/select")
    public String selectCart() {
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id", getId());
        List result = cartService.list(cartQueryWrapper);
        return getSuccessResult(result);
    }


}
