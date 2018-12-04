package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Laundry;
import com.yniot.lms.db.entity.Wardrobe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WardrobeMapper extends CommonMapper<Wardrobe> {

}