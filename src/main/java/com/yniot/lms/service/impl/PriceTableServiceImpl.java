package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.PriceTableMapper;
import com.yniot.lms.db.entity.PriceTable;
import com.yniot.lms.service.PriceTableService;
import org.springframework.stereotype.Service;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-23 15:56
 **/
@Service
public class PriceTableServiceImpl extends ServiceImpl<PriceTableMapper, PriceTable> implements PriceTableService {
}
