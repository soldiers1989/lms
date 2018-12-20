package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.LaundryMapper;
import com.yniot.lms.db.entity.Laundry;
import com.yniot.lms.service.LaundryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Integer> getMyLaundryIdList(int userId) {
        return baseMapper.getMyLaundryIdList(userId);
    }

    @Override
    public IPage getMyLaundryList(int userId, String keyWord, int pageNum, int pageSize) {
        QueryWrapper<Laundry> laundryQueryWrapper = new QueryWrapper<>();
        laundryQueryWrapper.eq("user_id", userId);
        if (StringUtils.isNotEmpty(keyWord)) {
            laundryQueryWrapper.like("name", keyWord);
        }
        return this.page(new Page<>(pageNum, pageSize), laundryQueryWrapper);
    }


    @Override
    public IPage getAllLaundry(String keyWord, int pageSize, int pageNum) {
        QueryWrapper<Laundry> laundryQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            laundryQueryWrapper.like("name", keyWord)
                    .or().like("address", keyWord)
                    .or().like("phone", keyWord);
        }
        return this.page(new Page<>(pageNum, pageSize), laundryQueryWrapper);
    }


    @Override
    public IPage getForRelate(int userId, String keyWord, int pageSize, int pageNum) {
        QueryWrapper<Laundry> laundryQueryWrapper = new QueryWrapper<>();
        laundryQueryWrapper.eq("user_id", userId)
                .or().isNull("user_id")
                .or().le("user_id", 0);
        return this.page(new Page<>(pageNum, pageSize), laundryQueryWrapper);
    }

    @Override
    public int relate(List<Integer> laundryIdList, int userId) {
        if (laundryIdList == null || laundryIdList.isEmpty()) {
            return 0;
        }
        return baseMapper.relate(laundryIdList, userId);
    }
}
