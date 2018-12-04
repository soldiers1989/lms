package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Laundry;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 14:57
 **/
public interface LaundryService extends IService<Laundry> {
    Laundry getByWardrobeId(int wardrobeId);

}
