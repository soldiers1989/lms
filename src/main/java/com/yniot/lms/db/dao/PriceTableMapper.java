package com.yniot.lms.db.dao;

import com.yniot.lms.db.entity.PriceTable;
import com.yniot.exclude.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
@Mapper
public interface PriceTableMapper extends CommonMapper<PriceTable> {
    @Select("select AVG(price) from biz_price_table where goods_id = #{goodsId} and deleted =0")
    BigDecimal getAvgPrice(int goodsId);
}
