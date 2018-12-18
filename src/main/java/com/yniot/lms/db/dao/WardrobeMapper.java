package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Wardrobe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
@Mapper
public interface WardrobeMapper extends CommonMapper<Wardrobe> {

    int updateAllCellNum(List<Integer> wardrobeIdList);

    int relateLaundry(boolean relate, int laundryId, @Param("list") List<Integer> wardrobeIdList);

    int activate(int activate, @Param("list") List<Integer> wardrobeIdList);

    int updateWardrobeInfo();

    List<Wardrobe> selectForRelate(String keyWord, int pageNum, int pageSize, int laundryId);
}
