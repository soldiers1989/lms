package com.yniot.lms.service;

import com.yniot.lms.controller.commonController.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

public class BaseService extends BaseController {
    @Value("${system.config.admin.level}")
    protected int ADMIN_LEVEL;
    @Value("${database.sql.max}")
    protected int MAX_SQL_SIZE;

    /**
     * 配合like操作
     *
     * @param content
     * @return
     */
    public String appendLike(String content) {
        return "%" + content + "%";
    }
}
