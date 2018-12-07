package com.yniot.lms.db.dao;

import com.yniot.lms.db.entity.CellPassword;
import com.yniot.exclude.CommonMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanggl
 * @since 2018-12-07
 */
public interface CellPasswordMapper extends CommonMapper<CellPassword> {
    @Select("select password from biz_cell_password where (expired=0 or sysdate()> expire_time) and order_id = #{orderId}")
    String getByOrderId(int orderId);
}
