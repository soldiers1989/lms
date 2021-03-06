package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.ApplicationMapper;
import com.yniot.lms.db.entity.Application;
import com.yniot.lms.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 15:12
 **/
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {
    @Override
    public int approveOrDeny(boolean approve, List<Integer> applicationIdList) {
        return baseMapper.approveOrDeny(approve, applicationIdList);
    }
}
