package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Income;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanggl
 * @since 2018-12-14
 */
public interface IncomeMapper extends CommonMapper<Income> {
    int saveIncome(int orderId);
}
