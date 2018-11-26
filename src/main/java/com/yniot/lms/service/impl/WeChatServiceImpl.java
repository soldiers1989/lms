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
        this.sendMessage("你的账户进行了登陆操作", "若为本人操作,可忽略本条消息",
                null, openId, LOGIN_MSG_TEMPLATE_ID, CommonUtil.Date.getNowDate(), host);
    }



    /**
     * @Author wanggl(lane)
     * @Description //TODO 通用微信推送消息方法
     * 适用于以下以下形式
     * {{first.DATA}}
     * 名称1：{{keyword1.DATA}}
     * 名称2：{{keyword2.DATA}}
     * 名称3：{{keyword3.DATA}}
     * 名称4：{{keyword4.DATA}}
     *  .........
     * 名称N：{{keywordN.DATA}}
     * {{remark.DATA}}
     *
     * @Date 下午7:55 2018/11/26
     * @Param [first, remark, url, openId, templateId, keywords]
     * @return void
     **/
    private void sendMessage(String first, String remark, String url, String openId, String templateId, String... keywords) throws WxErrorException {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(templateId);
        //根据id生产url
        wxMpTemplateMessage.setUrl(url);
        wxMpTemplateMessage.setToUser(openId);
        List<WxMpTemplateData> wxMpTemplateDataList = new ArrayList<>();
        WxMpTemplateData wxMpTemplateDataFirst = new WxMpTemplateData();
        WxMpTemplateData wxMpTemplateDataLast = new WxMpTemplateData();
        wxMpTemplateDataFirst.setName("first");
        wxMpTemplateDataFirst.setValue(first);
        wxMpTemplateDataLast.setName("remark");
        wxMpTemplateDataLast.setValue(remark);
        wxMpTemplateDataList.add(wxMpTemplateDataFirst);
        wxMpTemplateDataList.add(wxMpTemplateDataLast);
        for (int i = 1; i < keywords.length; i++) {
            WxMpTemplateData wxMpTemplateDataTemp = new WxMpTemplateData();
            wxMpTemplateDataTemp.setName(KEYWORD_KEY + i);
            wxMpTemplateDataTemp.setValue(keywords[i]);
            wxMpTemplateDataList.add(wxMpTemplateDataTemp);
        }
        wxMpTemplateMessage.setData(wxMpTemplateDataList);
        String result = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        logger.info("推送结果:" + result);
    }


}
