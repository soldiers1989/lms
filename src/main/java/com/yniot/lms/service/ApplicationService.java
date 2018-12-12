package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Application;

import java.util.List;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 15:11
 **/
public interface ApplicationService extends IService<Application> {

    int approveOrDeny(boolean approve, List<Integer> applicationIdList);
}
