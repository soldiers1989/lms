package com.yniot.lms.service.impl;

import com.yniot.lms.db.dao.LaundryMapper;
import com.yniot.lms.db.entity.Laundry;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.service.LaundryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
@Service
public class LaundryServiceImpl extends ServiceImpl<LaundryMapper, Laundry> implements LaundryService {

    @Override
    public Laundry getByWardrobeId(int wardrobeId) {
        return baseMapper.getByWardrobeId(wardrobeId);
    }

}
