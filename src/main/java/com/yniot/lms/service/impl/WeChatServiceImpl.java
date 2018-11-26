package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.dao.WeChatConfigMapper;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.service.WeChatService;
import com.yniot.lms.utils.CommonUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-21 14:21
 **/
@Service
public class WeChatServiceImpl extends ServiceImpl<WeChatConfigMapper, WeChatConfig> implements WeChatService {
    private static Logger logger = Logger.getLogger(WeChatServiceImpl.class);


    @Autowired
    CacheDao cacheDao;
    @Autowired
    WxMpService wxMpService;

    @Override
    public WeChatConfig getConfig() {
        //1.先判断缓存是否有配置
        WeChatConfig weChatConfig = cacheDao.get(CONFIG_KEY, WeChatConfig.class);
        //2.没有则直接读数据库,并写入缓存
        if (weChatConfig == null) {
            QueryWrapper<WeChatConfig> weChatConfigQueryWrapper = new QueryWrapper<>();
            weChatConfig = super.getOne(weChatConfigQueryWrapper);
            if (weChatConfig != null) {
                cacheDao.set(CONFIG_KEY, weChatConfig);
            }
        }
        return weChatConfig;
    }

    @Override
    public int updateConfig(WeChatConfig weChatConfig) {
        cacheDao.delete(CONFIG_KEY);
        return super.saveOrUpdate(weChatConfig) ? 1 : 0;
    }

    @Override
    public void sendLoginNotice(String openId, String host) throws WxErrorException {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(LOGIN_MSG_TEMPLATE_ID);
        //根据id生产url
        wxMpTemplateMessage.setUrl("https://www.baidu.com");
        wxMpTemplateMessage.setToUser(openId);
        List<WxMpTemplateData> wxMpTemplateDataList = new ArrayList<>();
        //    {{first.DATA}}
        //    登陆时间：{{keyword1.DATA}}
        //    登陆Ip：{{keyword2.DATA}}
        //    {{remark.DATA}}
        WxMpTemplateData wxMpTemplateData1 = new WxMpTemplateData();
        WxMpTemplateData wxMpTemplateData2 = new WxMpTemplateData();
        WxMpTemplateData wxMpTemplateData3 = new WxMpTemplateData();
        WxMpTemplateData wxMpTemplateData4 = new WxMpTemplateData();

        wxMpTemplateData1.setName("first");
        wxMpTemplateData1.setValue("你的账户进行了登陆操作");
//        wxMpTemplateData1.setColor("#173177");

        wxMpTemplateData2.setName("keyword1");
        wxMpTemplateData2.setValue(CommonUtil.Date.getNowDate());
//        wxMpTemplateData2.setColor("#2F4F4F");

        wxMpTemplateData3.setName("keyword2");
        wxMpTemplateData3.setValue(host);
//        wxMpTemplateData3.setColor("#2F4F4F");

        wxMpTemplateData4.setName("remark");
        wxMpTemplateData4.setValue("若为本人操作，可忽略该条信息。");
//        wxMpTemplateData4.setColor("#173177");

//The SQL execution time is too large, please optimize !
        wxMpTemplateDataList.add(wxMpTemplateData1);
        wxMpTemplateDataList.add(wxMpTemplateData2);
        wxMpTemplateDataList.add(wxMpTemplateData3);
        wxMpTemplateDataList.add(wxMpTemplateData4);
        wxMpTemplateMessage.setData(wxMpTemplateDataList);
        String result = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        logger.info("推送结果:" + result);
    }


}
