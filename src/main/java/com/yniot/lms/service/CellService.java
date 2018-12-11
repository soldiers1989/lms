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
    String CELL_TOKEN_PREFIX = "CELL_TOKEN_";

    List<Cell> getCellListByWardrobeId(int wardrobeId);

    boolean usedCell(int cellId, int orderId);

    boolean releaseCellByCellId(int cellId);

    boolean releaseCellByOrderId(int orderId);

    int getAvailableCellId(int wardrobeId);

    int getAvailableCellNum(int wardrobeId);

    Cell getAvailableCell(int wardrobeId);

    void refreshCell();

    boolean createCell(int num, int wardrobeId);

    boolean activateCellByWardrobeId(int wardrobeId, boolean activate);

    boolean activateCellByWardrobeIdList(List<Integer> wardrobeIdList, boolean activate);

    boolean activateCellByCellId(boolean activate, int cellId);

}
