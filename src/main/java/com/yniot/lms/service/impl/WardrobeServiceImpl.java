package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.CellMapper;
import com.yniot.lms.db.dao.WardrobeMapper;
import com.yniot.lms.db.entity.Wardrobe;
import com.yniot.lms.service.CellService;
import com.yniot.lms.service.WardrobeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class WardrobeServiceImpl extends ServiceImpl<WardrobeMapper, Wardrobe> implements WardrobeService {

    @Override
    public boolean updateCellNum(int wardrobeId) {
        List<Integer> wardrobeIdList = new ArrayList<>();
        wardrobeIdList.add(wardrobeId);
        return updateAllCellNum(wardrobeIdList);
    }

    @Autowired
    CellService cellService;

    @Override
    public boolean updateAllCellNum(List<Integer> wardrobeIdList) {
        //更新可用格子状态
        cellService.refreshCell();
        return baseMapper.updateAllCellNum(wardrobeIdList) > 0;
    }

    @Override
    public int relateLaundry(boolean relate, int laundryId, List<Integer> wardrobeIdList) {
        return baseMapper.relateLaundry(relate, laundryId, wardrobeIdList);
    }


    @Override
    public int activate(boolean activate, List<Integer> wardrobeIdList) {
        if (wardrobeIdList == null || wardrobeIdList.isEmpty()) {
            return 0;
        }
        int re1 = baseMapper.activate(activate ? 1 : 0, wardrobeIdList);
        if (re1 == wardrobeIdList.size()) {
            cellService.activateCellByWardrobeIdList(wardrobeIdList, activate);
        }
        return re1;
    }
}
