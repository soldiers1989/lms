package com.yniot.lms.service;

import com.yniot.lms.db.entity.GoodsCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-06
 */
public interface GoodsCodeService extends IService<GoodsCode> {
    int isExists(String code);

    boolean useCode(String code, int orderId);

    boolean releaseCode(String code);

}
