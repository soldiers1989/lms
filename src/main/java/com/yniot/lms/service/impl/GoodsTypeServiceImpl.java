package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yniot.lms.db.entity.GoodsType;
import com.yniot.lms.db.dao.GoodsTypeMapper;
import com.yniot.lms.service.GoodsTypeService;
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
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {
    @Override
    public List<GoodsType> getAll() {
        QueryWrapper<GoodsType> goodsTypeQueryWrapper = new QueryWrapper<>();
        return list(goodsTypeQueryWrapper);
    }
}
