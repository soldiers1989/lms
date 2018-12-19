package com.yniot.lms.controller.commonController;

import com.yniot.lms.enums.OrderStateEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lane
 * @Date: 2018-12-19 11:36
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/enums")
public class EnumsController extends BaseController {
    @RequestMapping("/order/state")
    public String getOrderEnum() {
        return getSuccessResult(OrderStateEnum.toJson());
    }
}
