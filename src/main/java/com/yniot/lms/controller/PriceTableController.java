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

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-23 15:53
 **/
@LaundryOnly
@RestController
@RequestMapping(name = "/priceTable", produces = "text/plain;charset=UTF-8")
public class PriceTableController extends BaseControllerT<PriceTable> {

    @Autowired
    PriceTableService priceTableService;


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

    @RequestMapping("/select")
    public String selectPriceTable(@RequestParam(name = "keyWord", required = false, defaultValue = "") String keyWord,
                             @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") long pageNum,
                             @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "0") long pageSize) {
        QueryWrapper<PriceTable> priceTableQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            priceTableQueryWrapper.like("priceTable_name", keyWord).or().like("description", keyWord);
        }
        return super.getSuccessPage(priceTableService.page(new Page(pageNum, pageSize), priceTableQueryWrapper));
    }
}
