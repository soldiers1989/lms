package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.entity.Cell;
import com.yniot.lms.db.dao.CellMapper;
import com.yniot.lms.service.CellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.service.WardrobeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
@Service
public class CellServiceImpl extends ServiceImpl<CellMapper, Cell> implements CellService {

    @Override
    public List<Cell> getCellListByWardrobeId(int wardrobeId) {
        QueryWrapper<Cell> cellQueryWrapper = new QueryWrapper<>();
        cellQueryWrapper.eq("wardrobe_id", wardrobeId)
                .eq("in_used", 0)
                .eq("deleted", 0)
                .eq("closed", 1)
                .eq("activated", 1);
        return super.list(cellQueryWrapper);
    }

    @Override
    public Cell getAvailableCell(int wardrobeId) {
        List<Cell> cellList = getCellListByWardrobeId(wardrobeId);
        Cell cell = null;
        if (cellList != null && !cellList.isEmpty()) {
            cell = cellList.get(0);
        }
        return cell;
    }

    @Override
    public int getAvailableCellId(int wardrobeId) {
        Cell cell = getAvailableCell(wardrobeId);
        return cell != null ? cell.getId() : -1;
    }

    @Override
    public int getAvailableCellNum(int wardrobeId) {
        List<Cell> cellList = getCellListByWardrobeId(wardrobeId);
        return cellList != null ? cellList.size() : 0;
    }

    @Override
    public boolean usedCell(int cellId, int orderId) {
        return baseMapper.usedCell(cellId, orderId) > 0;
    }

    @Override
    public boolean releaseCellByCellId(int cellId) {
        return baseMapper.releaseCellByCellId(cellId) > 0;
    }


    @Override
    public boolean releaseCellByOrderId(int orderId) {
        return baseMapper.releaseCellByOrderId(orderId) > 0;
    }

    @Override
    public void refreshCell() {
        baseMapper.refreshCellState();
    }

    @Autowired
    WardrobeService wardrobeService;

    @Override
    public boolean createCell(int num, int wardrobeId) {
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Cell cell = new Cell();
            cell.setWardrobeId(wardrobeId);
            cell.setActivated(true);
            cell.setClosed(true);
            cell.setCreateTime(LocalDateTime.now());
            cell.setInUsed(false);
            cellList.add(cell);
        }
        return saveBatch(cellList) && wardrobeService.updateAllCellNum();
    }

    @Override
    public boolean activateCellByWardrobeId(int wardrobeId, boolean activate) {
        return baseMapper.activateCell(wardrobeId, activate ? 1 : 0) > 0;
    }

    @Override
    public boolean activateCellByCellId(boolean activate, int cellId) {
        return baseMapper.activateCellByCellId(activate ? 1 : 0, cellId) > 0;
    }

    @Override
    public boolean activateCellByWardrobeIdList(List<Integer> wardrobeIdList, boolean activate) {
        return baseMapper.activateCellBatch(wardrobeIdList, activate ? 1 : 0) > 0;
    }
}
