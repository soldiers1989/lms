package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.GoodsTypeMapper;
import com.yniot.lms.db.entity.GoodsType;
import com.yniot.lms.service.GoodsTypeService;
import org.springframework.stereotype.Service;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 20:03
 **/
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {
}
