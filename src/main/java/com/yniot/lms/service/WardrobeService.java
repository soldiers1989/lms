package com.yniot.lms.service;

import com.yniot.lms.db.entity.Wardrobe;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
public interface WardrobeService extends IService<Wardrobe> {

    boolean updateCellNum(int wardrobeId);

    boolean updateAllCellNum(List<Integer> wardrobeIdLisst);

    int relateLaundry(boolean relate, int laundryId, List<Integer> wardrobeIdList);

    int activate(boolean activate, List<Integer> wardrobeIdList);

    boolean exists(String wardrobeCode);

    boolean createWardrobe(Wardrobe wardrobe);
}
