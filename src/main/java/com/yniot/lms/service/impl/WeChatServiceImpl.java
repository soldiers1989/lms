package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.dao.WeChatConfigMapper;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.db.entity.WeChatConfigExample;
import com.yniot.lms.service.WeChatService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.RandomStringUtils;
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
    @Autowired
    WxMpInMemoryConfigStorage config;
    @Autowired
    WxMpService wxMpService;

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


    /**
     * @Author wanggl
     * @Description 发消息
     * @Date 21:25 2018-11-21
     * @Param []
     * @return int
     **/
    @Override
    public int sendMessage() throws WxErrorException {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().toUser("").templateId("").url("").build();
        templateMessage.addData(new WxMpTemplateData("first", "", "#FF00FF"))
                .addData(new WxMpTemplateData("remark", RandomStringUtils.randomAlphanumeric(100), "#FF00FF"));
        this.wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        return 0;
    }
}
