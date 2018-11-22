package com.yniot.lms.controller.commonController;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Author wanggl
 * @Description 通用方法
 * @create 10:21 2018-11-22
 * @modify
 **/
public class BaseControllerT<T> extends BaseController {

    public String getSuccessPage(IPage<T> page) {
        return super.getSuccessResult(page.getRecords(), page.getPages(), page.getSize(), page.getTotal());
    }
}
