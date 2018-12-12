package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Catalog;
import com.yniot.lms.service.CatalogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @project: lms
 * @description:
 * @catalogor: wanggl
 * @create: 2018-11-22 20:03
 **/
@AdminOnly
@RestController
@RequestMapping(value = "/catalog", produces = {"application/json;charset=UTF-8"})
public class CatalogController extends BaseControllerT<Catalog> {
    @Autowired
    CatalogService catalogService;

    @RequestMapping("/select")
    public String select(@RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
                         @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "20000") int pageSize,
                         @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<Catalog> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyWord)) {
            queryWrapper.like("type_name", keyWord).or()
                    .like("description", keyWord);
        }
        if (pageSize <= 0) {
            return super.getSuccessResult(catalogService.list(queryWrapper));
        } else {
            return super.getSuccessPage(catalogService.page(new Page(pageNum, pageSize), queryWrapper));
        }
    }

    @RequestMapping("/create")
    public String createCatalog(@RequestBody Catalog catalog) {
        return super.getSuccessResult(catalogService.save(catalog));
    }

    @RequestMapping("/update")
    public String updateCatalog(@RequestBody Catalog catalog) {
        return super.getSuccessResult(catalogService.saveOrUpdate(catalog));
    }

    @RequestMapping("/delete")
    public String deleteCatalog(@RequestParam(name = "catalogId") int catalogId) {
        return super.getSuccessResult(catalogService.removeById(catalogId));
    }

    @RequestMapping("/selectById")
    public String select(@RequestParam(name = "id") int catalogId) {
        return super.getSuccessResult(catalogService.getById(catalogId));
    }

}
