package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.annotation.Unfinished;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Laundry;
import com.yniot.lms.service.CouponService;
import com.yniot.lms.service.LaundryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/laundry")
@RestController
public class LaundryController extends BaseControllerT<Laundry> {
    private static Logger logger = Logger.getLogger(LaundryController.class);
    @Autowired
    LaundryService laundryService;

    @Autowired
    CouponService couponService;

    //

    /**
     * @return java.lang.String
     * @Author wanggl
     * @Description 6.获取洗衣店, 按坐标位置远近排序
     * @create 15:09 2018-11-22
     * @modify 15:09 2018-11-22
     * @Param [pageSize, pageNum, latitude, longitude]
     **/
    @Unfinished
    @RequestMapping("/getNearestLaundryList")
    public String getNearestLaundryList(@RequestParam(name = "pageSize", required = false, defaultValue = "0") int pageSize,
                                        @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
                                        @RequestParam(name = "latitude") float latitude,
                                        @RequestParam(name = "longitude") float longitude) {

        Wrapper<Laundry> laundryWrapper = new QueryWrapper<Laundry>();
        Page<Laundry> laundryPage = new Page<>();
        if (pageSize > 0) {
            laundryPage.setSize(pageSize);
        }
        laundryPage.setPages(pageNum);
        return super.getSuccessPage(laundryService.page(laundryPage, laundryWrapper));
    }

    @AdminOnly
    @RequestMapping("/select")
    public String getLaundryList(@RequestParam(name = "keyWord", required = false, defaultValue = "") String keyWord,
                                 @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize,
                                 @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<Laundry> laundryWrapper = new QueryWrapper<Laundry>();
        if (StringUtils.isNotEmpty(keyWord)) {
            laundryWrapper.like("name", keyWord)
                    .or().like("address", keyWord)
                    .or().like("phone", keyWord);
        }
        Page<Laundry> laundryPage = new Page<>(pageNum, pageSize);
        return super.getSuccessPage(laundryService.page(laundryPage, laundryWrapper));
    }

    //7.洗衣店信息修改
    @RequestMapping("/update")
    public String updateLaundry(@RequestBody Laundry laundry) {
        return super.getSuccessResult(laundryService.updateById(laundry));
    }

    @RequestMapping("/create")
    public String create(@RequestBody Laundry laundry) {



        return super.getSuccessResult(laundryService.updateById(laundry));
    }

    //8.发放优惠劵
    @RequestMapping("/coupon/send")
    @Unfinished
    public String sendCoupon(@RequestParam(name = "userIdList[]") List<Integer> userIdList) {
        return super.getSuccessResult("");
    }

}
