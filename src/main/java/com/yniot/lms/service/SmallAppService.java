package com.yniot.lms.service;

import com.yniot.lms.db.entity.SmallAppConfig;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @program: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-21 14:20
 **/
public interface SmallAppService {
    String KEYWORD_KEY = "keyword";

    void initConfig();

}
