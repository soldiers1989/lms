package com.yniot.lms.service;

import com.yniot.lms.db.entity.Wardrobe;
import com.baomidou.mybatisplus.extension.service.IService;

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
    boolean updateAllCellNum();
}
