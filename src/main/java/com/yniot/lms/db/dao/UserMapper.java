package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper  extends CommonMapper<User> {
}