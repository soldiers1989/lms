package com.yniot.lms.service;

import com.yniot.lms.db.entity.RelUserApp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-24
 */
public interface RelUserAppService extends IService<RelUserApp> {
    String getOpenIdByAppId(String appId, int userId);
    boolean createRel(String appId, String openId, int userId, String phone);
}
