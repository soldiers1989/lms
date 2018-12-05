package com.yniot.lms.controller;


import com.yniot.lms.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/cell")
public class CellController extends BaseController {
    @Autowired
    CellService cellService;

    @RequestMapping("/getAvailable")
    public String getAvailable(int wardrobeId) {
        return getSuccessResult(cellService.getAvailableCellNum(wardrobeId));
    }
}

