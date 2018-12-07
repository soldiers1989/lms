package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.CellPassword;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanggl
 * @since 2018-12-07
 */
public interface CellPasswordMapper extends CommonMapper<CellPassword> {

    @Select("select password from biz_cell_password where sysdate()< expire_time and order_id = #{orderId} and used=0")
    String getByOrderId(int orderId);

    /**
     * @return java.util.List<java.lang.Integer>
     * @Author wanggl(lane)
     * @Description //TODO 获取已过期但未使用的密码,用于生成新的密码
     * @Date 15:28 2018-12-07
     * @Param []
     **/
    @Select("select order_id from biz_cell_password where sysdate()> expire_time  and used=0")
    List<Integer> getPasswordExpiredOrderId();


}
