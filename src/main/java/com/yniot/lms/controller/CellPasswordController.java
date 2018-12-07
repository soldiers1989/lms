package com.yniot.lms.controller;


import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.service.CellPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanggl
 * @since 2018-12-07
 */
@RestController
@RequestMapping("/cellPassword")
public class CellPasswordController extends BaseController {

    @Autowired
    CellPasswordService cellPasswordService;

    @RequestMapping("/getByOrderId")
    public String getByOrderId(@RequestParam Integer orderId) {
        return getSuccessResult(cellPasswordService.getByOrderId(orderId));
    }
}

