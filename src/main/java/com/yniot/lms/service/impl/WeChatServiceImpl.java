package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.dao.WeChatConfigMapper;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.db.entity.WeChatConfigExample;
import com.yniot.lms.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-21 14:21
 **/
@Service
public class WeChatServiceImpl extends ServiceImpl<WeChatConfigMapper, WeChatConfig> implements WeChatService {
    @Autowired
    WeChatConfigMapper weChatConfigMapper;
    @Autowired
    CacheDao cacheDao;

    private static String CONFIG_KEY = "WeChatAccountConfig";


    @Override
    public WeChatConfig getConfig() {
        //1.先判断缓存是否有配置
        WeChatConfig weChatConfig = cacheDao.get(CONFIG_KEY, WeChatConfig.class);
        //2.没有则直接读数据库,并写入缓存
        if (weChatConfig == null) {
            WeChatConfigExample weChatConfigExample = new WeChatConfigExample();
            weChatConfigExample.or().andActivatedEqualTo(true);
            List<WeChatConfig> weChatConfigList = weChatConfigMapper.selectByExample(weChatConfigExample);
            if (!weChatConfigList.isEmpty()) {
                weChatConfig = weChatConfigList.get(0);
                cacheDao.set(CONFIG_KEY, weChatConfig);
            }
        }
        return weChatConfig;
    }

    @Override
    public int updateConfig(WeChatConfig weChatConfig) {
        cacheDao.delete(CONFIG_KEY);
        return weChatConfigMapper.updateByPrimaryKeySelective(weChatConfig);
    }


}
