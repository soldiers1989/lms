package com.yniot.lms.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.config.WxMaConfiguration;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.dao.SmallAppConfigMapper;
import com.yniot.lms.db.entity.SmallAppConfig;
import com.yniot.lms.service.SmallAppService;
import me.chanjar.weixin.common.error.WxErrorException;
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
public class SmallAppServiceImpl extends ServiceImpl<SmallAppConfigMapper, SmallAppConfig> implements SmallAppService {
    private static Logger logger = Logger.getLogger(SmallAppServiceImpl.class);


    @Autowired
    CacheDao cacheDao;


    /**
     * @return void
     * @Author wanggl(lane)
     * @Description //TODO 通用微信推送消息方法
     * 适用于以下以下形式
     * {{first.DATA}}
     * 名称1：{{keyword1.DATA}}
     * 名称2：{{keyword2.DATA}}
     * 名称3：{{keyword3.DATA}}
     * 名称4：{{keyword4.DATA}}
     * .........
     * 名称N：{{keywordN.DATA}}
     * {{remark.DATA}}
     * @Date 下午7:55 2018/11/26
     * @Param [first, remark, url, openId, templateId, keywords]
     **/
    private void sendSmallAppMessage(String appId, String first, String remark, String page, String openId, String templateId, String... keywords) throws WxErrorException {
        WxMaTemplateMessage wxMaTemplateMessage = new WxMaTemplateMessage();
        wxMaTemplateMessage.setTemplateId(templateId);
        wxMaTemplateMessage.setPage(page);
        wxMaTemplateMessage.setToUser(openId);
        List<WxMaTemplateData> wxMaTemplateDataList = new ArrayList<>();
        WxMaTemplateData wxMaTemplateDataFirst = new WxMaTemplateData();
        WxMaTemplateData wxMaTemplateDataLast = new WxMaTemplateData();
        wxMaTemplateDataFirst.setName("first");
        wxMaTemplateDataFirst.setValue(first);
        wxMaTemplateDataLast.setName("remark");
        wxMaTemplateDataLast.setValue(remark);
        wxMaTemplateDataList.add(wxMaTemplateDataFirst);
        wxMaTemplateDataList.add(wxMaTemplateDataLast);
        for (int i = 0; i < keywords.length; i++) {
            WxMaTemplateData wxMaTemplateDataTemp = new WxMaTemplateData();
            wxMaTemplateDataTemp.setName(KEYWORD_KEY + (i + 1));
            wxMaTemplateDataTemp.setValue(keywords[i]);
            wxMaTemplateDataList.add(wxMaTemplateDataTemp);
        }
        wxMaTemplateMessage.setData(wxMaTemplateDataList);
        WxMaService wxMaService = WxMaConfiguration.getMaServices().get(appId);
        wxMaService.getMsgService().sendTemplateMsg(wxMaTemplateMessage);
    }


    @Override
    public void initConfig() {

    }
}
