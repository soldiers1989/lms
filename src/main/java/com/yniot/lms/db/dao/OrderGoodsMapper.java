package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.OrderGoods;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderGoodsMapper extends CommonMapper<OrderGoods> {

    @Update("update biz_order_goods set state = #{state} where order_id =#{orderId} ")
    int updateState(Integer orderId, int state);

//    @Update("update biz_order_goods set state = 0 where order_id IN ( SELECT id FROM biz_order WHERE (state >= 30 AND state < 40) or state=58 )")
//    int refreshState();

}