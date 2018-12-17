package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.db.entity.GoodsCode;
import com.yniot.lms.db.dao.GoodsCodeMapper;
import com.yniot.lms.service.GoodsCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    @Override
    public int isExists(String code) {
        return baseMapper.isExists(code);
    }

    @Override
    public int relateCode(List<String> codeList, int orderGoodsId) {
        return baseMapper.setState(codeList, 1, orderGoodsId);
    }

    @Override
    public int releaseCode(List<String> codeList) {
        return baseMapper.setState(codeList, 0, 0);
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
}
