package com.yniot.lms.db.dao;

import com.yniot.lms.db.entity.Cell;
import com.yniot.exclude.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
public interface CellMapper extends CommonMapper<Cell> {
    @Update("update biz_cell set in_used = 0 ,order_id=0 where order_id = #{orderId}")
    int releaseCellByOrderId(int orderId);


    @Update("update biz_cell set in_used = 0 ,order_id=0 where id = #{cellId}")
    int releaseCellByCellId(int cellId);

    @Update("update biz_cell set in_used = 0 ,order_id=#{orderId} where id = #{cellId}")
    int usedCell(int cellId, int orderId);

    @Update("update biz_cell set in_used = 0 where order_id IN ( SELECT id FROM biz_order WHERE (state >= 30 AND state < 40) or state=58 )")
    int refreshCellState();

    @Update("update biz_cell set activate = #{activate} where wardrobe_id = #{wardrobeId}")
    int activateCell(int wardrobeId, int activate);

    int activateCellBatch(List<Integer> wardrobeIdList, int activate);

    @Update("update biz_cell set activate = #{activate} where id = #{cellId}")
    int activateCellByCellId(int activate, int cellId);
}
