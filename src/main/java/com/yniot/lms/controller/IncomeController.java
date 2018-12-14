package com.yniot.lms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.GoodsType;
import com.yniot.lms.db.entity.Income;
import com.yniot.lms.service.IncomeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2018-12-14
 */
@RestController
@RequestMapping("/income")
public class IncomeController extends BaseControllerT<Income> {
    @Autowired
    IncomeService incomeService;

    @RequestMapping("/select")
    public String select(@RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
                         @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "20") int pageSize,
                         @RequestParam(name = "withdrew", required = false, defaultValue = "-1") int withdrew,
                         @RequestParam(name = "createStartTimestamp", required = false, defaultValue = "0") long createStartTimestamp,
                         @RequestParam(name = "createEndTimestamp", required = false, defaultValue = "0") long createEndTimestamp,
                         @RequestParam(name = "withdrewStartTimestamp", required = false, defaultValue = "0") long withdrewStartTimestamp,
                         @RequestParam(name = "withdrewEndTimestamp", required = false, defaultValue = "0") long withdrewEndTimestamp,
                         @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum) {
        QueryWrapper<Income> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(keyWord)) {
            queryWrapper.like("laundryName", keyWord).or()
                    .like("actIncome", keyWord).or()
                    .like("actCost", keyWord).or()
                    .like("laundryId", keyWord).or()
                    .like("dividePercent", keyWord).or()
                    .like("orderCode", keyWord);
        }
//        if (withdrew >= 0) {
//            queryWrapper.eq("withdrew", withdrew);
//        }
//        queryWrapper.between("withdrew_time", withdrewStartTimestamp, withdrewEndTimestamp);
//        queryWrapper.between("create_time", createStartTimestamp, createEndTimestamp);
        if (pageSize <= 0) {
            return super.getSuccessResult(incomeService.list(queryWrapper));
        } else {
            return super.getSuccessPage(incomeService.page(new Page(pageNum, pageSize), queryWrapper));
        }
    }


    @AdminOnly
    @RequestMapping("/withdrew")
    public String withdrew(int orderId) {
        Income income = incomeService.getById(orderId);
        income.setWithdrew(true);
        income.setWithdrewTime(LocalDateTime.now());
        return getSuccessResult(incomeService.saveOrUpdate(income));
    }
}

