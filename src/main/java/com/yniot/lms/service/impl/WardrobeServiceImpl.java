package com.yniot.lms.service.impl;

import com.yniot.lms.db.entity.Cell;
import com.yniot.lms.db.entity.Wardrobe;
import com.yniot.lms.db.dao.WardrobeMapper;
import com.yniot.lms.service.CellService;
import com.yniot.lms.service.WardrobeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    CellService cellService;


    @Override
    public int getAvailableCell(int wardrobeId) {

        return 0;
    }
}
