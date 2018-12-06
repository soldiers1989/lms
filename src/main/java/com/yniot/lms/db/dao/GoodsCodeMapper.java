package com.yniot.lms.db.dao;

import com.yniot.lms.db.entity.GoodsCode;
import com.yniot.exclude.CommonMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanggl
 * @since 2018-12-06
 */
public interface GoodsCodeMapper extends CommonMapper<GoodsCode> {

    @Select("select count(1) from biz_goods_code where unique_code = #{code}")
    int isExists(String code);

    @Update("update biz_goods_code set used = #{state} ,order_id = #{orderId},modify_time=sysdate() where unique_code = #{code}")
    int setState(String code, int state, int orderId);

}
