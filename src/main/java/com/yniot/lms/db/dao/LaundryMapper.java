package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.Laundry;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LaundryMapper extends CommonMapper<Laundry> {
    @Select("select * from biz_laundry where id in (select laundry_id from biz_wardrobe where id = #{wardrobeId}) limit 0,1")
    Laundry getByWardrobeId(int wardrobeId);

}