package com.yniot.lms.controller;


import com.yniot.lms.db.entity.GoodsCode;
import com.yniot.lms.service.GoodsCodeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yniot.lms.controller.commonController.BaseController;

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
@RequestMapping("/goodsCode")
public class GoodsCodeController extends BaseController {
    @Autowired
    GoodsCodeService goodsCodeService;

    @RequestMapping("/create")
    public String createCode(String uniqueCode,
                             @RequestParam(name = "description", required = false, defaultValue = "") String description) {
        GoodsCode goodsCode = new GoodsCode();
        goodsCode.setCreateTime(LocalDateTime.now());
        goodsCode.setDescription(description);
        goodsCode.setUniqueCode(uniqueCode);
        goodsCode.setDeleted(false);
        goodsCode.setLaundryId(getLaundryId());
        goodsCode.setUsed(false);
        return getSuccessResult(goodsCodeService.save(goodsCode));
    }

    @RequestMapping("/checkCode")
    public String checkCode(String code) {
        return getSuccessResult(goodsCodeService.isExists(code));
    }

}

