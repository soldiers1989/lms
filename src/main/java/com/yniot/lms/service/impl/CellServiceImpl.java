package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.db.entity.Cell;
import com.yniot.lms.db.dao.CellMapper;
import com.yniot.lms.service.CellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
//        QueryWrapper<Cell> cellQueryWrapper = new QueryWrapper<>();
//        cellQueryWrapper.eq("id", cellId);
//        Cell cell = super.getOne(cellQueryWrapper);
//        cell.setInUsed(false);
//        cell.setOrderId(null);
        return baseMapper.releaseCellByCellId(cellId) > 0;
    }

    @Override
    public boolean releaseCellByOrderId(int orderId) {
//        QueryWrapper<Cell> cellQueryWrapper = new QueryWrapper<>();
//        cellQueryWrapper.eq("order_id", orderId);
//        List<Cell> cellList = super.list(cellQueryWrapper);
//        for (Cell cell : cellList) {
//            cell.setInUsed(false);
//            cell.setOrderId(null);
//        }
        return baseMapper.releaseCellByOrderId(orderId) > 0;
    }

    @Override
    public void refreshCell() {
        baseMapper.refreshCellState();
    }
}
