package com.yniot.lms.service.impl;

import com.yniot.lms.db.entity.GoodsCode;
import com.yniot.lms.db.dao.GoodsCodeMapper;
import com.yniot.lms.service.GoodsCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-06
 */
@Service
public class GoodsCodeServiceImpl extends ServiceImpl<GoodsCodeMapper, GoodsCode> implements GoodsCodeService {
    @Override
    public int isExists(String code) {
        return baseMapper.isExists(code);
    }

    @Override
    public boolean useCode(String code, int orderId) {
        return baseMapper.setState(code, 1, orderId) > 0;
    }

    @Override
    public boolean releaseCode(String code) {
        return baseMapper.setState(code, 0, -1) > 0;
    }
}
