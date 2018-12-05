package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.WardrobeMapper;
import com.yniot.lms.db.entity.Wardrobe;
import com.yniot.lms.service.WardrobeService;
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

    @Override
    public boolean updateCellNum(int wardrobeId) {


        return false;
    }
}
