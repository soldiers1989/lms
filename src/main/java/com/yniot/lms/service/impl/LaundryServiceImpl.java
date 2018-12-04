package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.LaundryMapper;
import com.yniot.lms.db.entity.Laundry;
import com.yniot.lms.service.LaundryService;
import org.springframework.stereotype.Service;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 14:57
 **/
@Service
public class LaundryServiceImpl extends ServiceImpl<LaundryMapper, Laundry> implements LaundryService {
    @Override
    public Laundry getByWardrobeId(int wardrobeId) {
        return baseMapper.getByWardrobeId(wardrobeId);
    }
}
