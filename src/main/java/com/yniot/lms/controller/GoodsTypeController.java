package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.GoodsType;
import com.yniot.lms.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @project: lms
 * @description:
 * @goodsTypeor: wanggl
 * @create: 2018-11-22 20:03
 **/
@AdminOnly
@RequestMapping("/goodsType")
@RestController
public class GoodsTypeController extends BaseControllerT<GoodsType> {
    @Autowired
    GoodsTypeService goodsTypeService;

    @RequestMapping("/select")
    public String select(@RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "0") String keyWord,
                         @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "0") int pageSize,
                         @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<GoodsType> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("type_name", keyWord).or()
                .like("description", keyWord);
        return super.getSuccessPage(goodsTypeService.page(new Page(pageNum, pageSize), queryWrapper));
    }

    @RequestMapping("/create")
    public String createGoodsType(@RequestBody GoodsType goodsType) {
        return super.getSuccessResult(goodsTypeService.save(goodsType));
    }

    @RequestMapping("/update")
    public String updateGoodsType(@RequestBody GoodsType goodsType) {
        goodsType.setModifyTime(new Date());
        return super.getSuccessResult(goodsTypeService.saveOrUpdate(goodsType));
    }

    @RequestMapping("/delete")
    public String deleteGoodsType(@RequestParam(name = "goodsTypeId") int goodsTypeId) {
        return super.getSuccessResult(goodsTypeService.removeById(goodsTypeId));
    }

    @RequestMapping("/selectById")
    public String select(@RequestParam(name = "id") int goodsTypeId) {
        return super.getSuccessResult(goodsTypeService.getById(goodsTypeId));
    }

}
