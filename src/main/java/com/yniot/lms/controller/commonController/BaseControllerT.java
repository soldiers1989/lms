package com.yniot.lms.controller.commonController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;

/**
 * @Author wanggl
 * @Description 通用方法
 * @create 10:21 2018-11-22
 * @modify  2018-12-19
 **/
public class BaseControllerT<T> extends BaseController {

    /**
     * @return java.lang.String
     * @Author wanggl(lane)
     * @Description //TODO mybatis plus 的page转换
     * @Date 10:08 2018-12-19
     * @Param [page]
     **/
    public String getSuccessPage(IPage<T> page) {
        return super.getSuccessResult(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }


    /**
     * @return java.lang.String
     * @Author wanggl(lane)
     * @Description //TODO mybatis pageHelper 的page转换
     * @Date 10:09 2018-12-19
     * @Param [page]
     **/
    public String getSuccessPage(Page page) {
        return super.getSuccessResult(page.getResult(), page.getPageNum(), page.getPageSize(), page.getTotal());
    }

}
