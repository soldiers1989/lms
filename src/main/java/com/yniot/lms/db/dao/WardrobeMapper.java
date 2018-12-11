package com.yniot.lms.db.dao;

import com.yniot.lms.db.entity.Wardrobe;
import com.yniot.exclude.CommonMapper;
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
public interface WardrobeMapper extends CommonMapper<Wardrobe> {
    @Update("update biz_wardrobe set ava_cell_num = ifnull((select sum(1) from biz_cell where wardrobe_id = #{wardrobeId} and activated=1 and deleted=0 and in_used=0),0)")
    int updateCellNum(int wardrobeId);

    @Update("update biz_wardrobe set ava_cell_num = ifnull((select sum(1) from biz_cell where biz_wardrobe.id = biz_cell.wardrobe_id and activated=1 and deleted=0 and in_used=0),0)" +
            ",total_cell_num = ifnull(( SELECT sum(1) FROM biz_cell WHERE biz_wardrobe.id = biz_cell.wardrobe_id  ),0 )")
    int updateAllCellNum();

    int relateLaundry(boolean relate, int wardrobeId, List<Integer> wardrobeIdList);

    int activate(int activate, List<Integer> wardrobeIdList);
}
