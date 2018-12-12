package com.yniot.lms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.LaundryOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.PriceTable;
import com.yniot.lms.service.PriceTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yniot.lms.controller.commonController.BaseController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanggl
 * @since 2018-12-04
 */
@RestController
@RequestMapping("/priceTable")
public class PriceTableController extends BaseControllerT<PriceTable> {
    @Autowired
    PriceTableService priceTableService;

    @RequestMapping("/select")
    public String select(@RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
                         @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "20") int pageSize,
                         @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<PriceTable> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            queryWrapper.like("type_name", keyWord).or()
                    .like("description", keyWord);
        }
        return super.getSuccessPage(priceTableService.page(new Page(pageNum, pageSize), queryWrapper));
    }

    @RequestMapping("/create")
    public String createPriceTable(@RequestBody PriceTable priceTable) {
        return super.getSuccessResult(priceTableService.save(priceTable));
    }

    @RequestMapping("/update")
    public String updatePriceTable(@RequestBody PriceTable priceTable) {
        return super.getSuccessResult(priceTableService.saveOrUpdate(priceTable));
    }

    @RequestMapping("/delete")
    public String deletePriceTable(@RequestParam(name = "priceTableId") int priceTableId) {
        return super.getSuccessResult(priceTableService.removeById(priceTableId));
    }

    @LaundryOnly
    @RequestMapping("/generate")
    public String generate() {
        return super.getSuccessResult(priceTableService.generate(getLaundryId()));
    }
}

