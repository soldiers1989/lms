package com.yniot.lms.service;

import com.yniot.lms.db.entity.Cell;
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
public interface CellService extends IService<Cell> {
    List<Cell> getCellListByWardrobeId(int wardrobeId);

    boolean usedCell(int cellId, int orderId);
}
