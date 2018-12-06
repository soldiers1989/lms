package com.yniot.lms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.GoodsCode;
import com.yniot.lms.service.GoodsCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanggl
 * @since 2018-12-06
 */
@RestController
@RequestMapping(value = "/goodsCode")
public class GoodsCodeController extends BaseControllerT<GoodsCode> {
    @Autowired
    GoodsCodeService goodsCodeService;

    @RequestMapping("/create")
    public String createCode(@RequestBody GoodsCode goodsCode) {
        if (isLaundry()) {
            if (StringUtils.isNotEmpty(goodsCode.getUniqueCode())) {
                goodsCode.setCreateTime(LocalDateTime.now());
                goodsCode.setDeleted(false);
                goodsCode.setLaundryId(getLaundryId());
                goodsCode.setUsed(false);
                goodsCode.setModifyTime(LocalDateTime.now());
                return getSuccessResult(goodsCodeService.save(goodsCode));
            }
            return getErrorMsg("uniqueCode is empty");
        } else {
            return noAuth();

        }
    }

    @RequestMapping("/checkCode")
    public String checkCode(@RequestBody GoodsCode goodsCode) {
        return getSuccessResult(goodsCodeService.isExists(goodsCode.getUniqueCode()));
    }

    @RequestMapping("/select")
    public String checkCode(@RequestParam(name = "keyWord", required = false, defaultValue = "") String keyWord,
                            @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
                            @RequestParam(name = "PageSize", required = false, defaultValue = "20") int PageSize) {
        QueryWrapper<GoodsCode> goodsCodeQueryWrapper = new QueryWrapper<>();
        goodsCodeQueryWrapper.eq("laundry_id", getLaundryId());
        if (StringUtils.isNotEmpty(keyWord)) {
            goodsCodeQueryWrapper.like("unique_code", keyWord);
        }
        return getSuccessPage(goodsCodeService.page(new Page<>(pageNum, PageSize), goodsCodeQueryWrapper));
    }

//    @RequestMapping("/logicDelete")
//    public String logicDelete(Integer id) {
//        return getSuccessResult(goodsCodeService.removeById(id));
//    }

}

