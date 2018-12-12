package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.PriceTable;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
public interface PriceTableService extends IService<PriceTable> {
    BigDecimal getAvgPrice(int goodsId);

    boolean generate(int laundryId);

    boolean remove(int laundryId);
}
