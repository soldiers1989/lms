package com.yniot.lms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Laundry;

import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 14:57
 **/
public interface LaundryService extends IService<Laundry> {
    Laundry getByWardrobeId(int wardrobeId);

    List<Integer> getMyLaundryIdList(int userId);

    IPage getMyLaundryList(int userId, String keyWord, int pageSize, int pageNum);

    IPage getAllLaundry(String keyWord, int pageSize, int pageNum);

    IPage getForRelate(int userId,String keyWord, int pageSize, int pageNum);

    int relate(List<Integer> laundryIdList, int userId);
}
