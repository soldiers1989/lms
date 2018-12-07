package com.yniot.lms.service;

import com.yniot.lms.db.entity.CellPassword;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-07
 */
public interface CellPasswordService extends IService<CellPassword> {
    String getByOrderId(Integer orderId);
}
