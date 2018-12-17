package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.db.entity.GoodsCode;
import com.yniot.lms.db.dao.GoodsCodeMapper;
import com.yniot.lms.db.entity.Order;
import com.yniot.lms.db.entity.OrderGoods;
import com.yniot.lms.service.GoodsCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.service.OrderGoodsService;
import com.yniot.lms.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-06
 */
@Service
public class GoodsCodeServiceImpl extends ServiceImpl<GoodsCodeMapper, GoodsCode> implements GoodsCodeService {
    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    OrderService orderService;


    @Override
    public int isExists(String code) {
        return baseMapper.isExists(code);
    }

    @Override
    public int relateCode(String code, int orderGoodsId) {
        return baseMapper.setState(code, 1, orderGoodsId);
    }

    @Override
    public int releaseCode(String code) {
        return baseMapper.setState(code, 0, 0);
    }

    @Override
    public List<GoodsCode> getByOrderGoodsId(int orderGoodsId) {
        List<Integer> orderGoodsIdList = new ArrayList<>();
        orderGoodsIdList.add(orderGoodsId);
        return getByOrderGoodsId(orderGoodsIdList);
    }

    @Override
    public List<GoodsCode> getByOrderGoodsId(List<Integer> orderGoodsIdList) {
        QueryWrapper<GoodsCode> goodsCodeQueryWrapper = new QueryWrapper<>();
        goodsCodeQueryWrapper.in("order_goods_id", orderGoodsIdList);
        return list(goodsCodeQueryWrapper);
    }


    @Override
    public int autoRelate(int orderId) {
        List<OrderGoods> orderGoodsList = orderGoodsService.getByOrderId(orderId);
        if (orderGoodsList == null || orderGoodsList.isEmpty()) {
            return 0;
        }
        int size = orderGoodsList.size();
        int cnt = 0;
        List<GoodsCode> goodsCodeList = getUnused(orderId, orderGoodsList.size());
        for (int i = 0; i < size; i++) {
            if (StringUtils.isEmpty(orderGoodsList.get(i).getTagCode())) {
                cnt += this.relateCode(goodsCodeList.get(i).getUniqueCode(), orderGoodsList.get(i).getId());
            }
        }
        return cnt;
    }

    @Override
    public List<GoodsCode> getUnused(int orderId, int cnt) {
        Order order = orderService.getById(orderId);
        QueryWrapper<GoodsCode> goodsCodeQueryWrapper = new QueryWrapper<>();
        goodsCodeQueryWrapper.eq("used", 0);
        goodsCodeQueryWrapper.eq("laundry_id", order.getLaundryId());
        if (cnt < 0) {
            return super.list(goodsCodeQueryWrapper);
        }
        IPage page = super.page(new Page(1, cnt), goodsCodeQueryWrapper);
        return page.getRecords();
    }
}
