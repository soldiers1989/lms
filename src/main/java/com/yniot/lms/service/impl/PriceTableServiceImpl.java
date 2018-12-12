package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.db.entity.GoodsType;
import com.yniot.lms.db.entity.Laundry;
import com.yniot.lms.db.entity.PriceTable;
import com.yniot.lms.db.dao.PriceTableMapper;
import com.yniot.lms.service.GoodsTypeService;
import com.yniot.lms.service.LaundryService;
import com.yniot.lms.service.PriceTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
@Service
public class PriceTableServiceImpl extends ServiceImpl<PriceTableMapper, PriceTable> implements PriceTableService {

    @Override
    public BigDecimal getAvgPrice(int goodsId) {
        return baseMapper.getAvgPrice(goodsId);
    }

    @Autowired
    GoodsTypeService goodsTypeService;
    @Autowired
    LaundryService laundryService;

    @Override
    public boolean generate(int laundryId) {
        Laundry laundry = laundryService.getById(laundryId);
        this.remove(laundryId);
        List<GoodsType> goodsTypeList = goodsTypeService.getAll();
        List<PriceTable> priceTableList = new ArrayList<>();
        for (GoodsType goodsType : goodsTypeList) {
            PriceTable priceTable = new PriceTable();
            priceTable.setPrice(goodsType.getAvgPrice());
            priceTable.setGoodsId(goodsType.getId());
            priceTable.setLaundryId(laundryId);
            priceTable.setDeleted(false);
            priceTable.setGoodsTypeName(goodsType.getName());
            priceTable.setCreateTime(LocalDateTime.now());
            priceTable.setLaundryName(laundry.getName());
            priceTable.setImgUrl(goodsType.getBannerImgUrl());
            priceTable.setDescription(goodsType.getDescription());
            priceTableList.add(priceTable);
        }
        return saveBatch(priceTableList);
    }

    @Override
    public boolean remove(int laundryId) {
        QueryWrapper<PriceTable> priceTableQueryWrapper = new QueryWrapper<>();
        priceTableQueryWrapper.eq("laundry_id", laundryId);
        return remove(priceTableQueryWrapper);
    }
}
