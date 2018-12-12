package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Application;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplicationMapper extends CommonMapper<Application> {


    int approveOrDeny(boolean approve, List<Integer> applicationIdList);
}