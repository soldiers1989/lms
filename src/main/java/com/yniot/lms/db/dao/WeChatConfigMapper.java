package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.db.entity.WeChatConfigExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WeChatConfigMapper  extends CommonMapper<WeChatConfig> {
    long countByExample(WeChatConfigExample example);

    int deleteByExample(WeChatConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WeChatConfig record);

    int insertSelective(WeChatConfig record);

    List<WeChatConfig> selectByExample(WeChatConfigExample example);

    WeChatConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WeChatConfig record, @Param("example") WeChatConfigExample example);

    int updateByExample(@Param("record") WeChatConfig record, @Param("example") WeChatConfigExample example);

    int updateByPrimaryKeySelective(WeChatConfig record);

    int updateByPrimaryKey(WeChatConfig record);
}