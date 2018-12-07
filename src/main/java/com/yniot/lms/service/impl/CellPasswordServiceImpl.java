package com.yniot.lms.service.impl;

import com.yniot.lms.db.entity.CellPassword;
import com.yniot.lms.db.dao.CellPasswordMapper;
import com.yniot.lms.service.CellPasswordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-07
 */
@Service
public class CellPasswordServiceImpl extends ServiceImpl<CellPasswordMapper, CellPassword> implements CellPasswordService {

    @Override
    public String getByOrderId(Integer orderId) {
        return baseMapper.getByOrderId(orderId);
    }
}
