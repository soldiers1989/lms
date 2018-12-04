package com.yniot.lms.service.impl;

import com.yniot.lms.db.entity.PriceTable;
import com.yniot.lms.db.dao.PriceTableMapper;
import com.yniot.lms.service.PriceTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
}
