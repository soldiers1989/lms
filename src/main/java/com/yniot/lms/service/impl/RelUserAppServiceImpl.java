package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.RelUserAppMapper;
import com.yniot.lms.db.entity.RelUserApp;
import com.yniot.lms.service.RelUserAppService;
import com.yniot.lms.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-24
 */
@Service
public class RelUserAppServiceImpl extends ServiceImpl<RelUserAppMapper, RelUserApp> implements RelUserAppService {
    @Override
    public String getOpenIdByAppId(String appId, int userId) {
        QueryWrapper<RelUserApp> relUserAppQueryWrapper = new QueryWrapper<>();
        relUserAppQueryWrapper.eq("app_id", appId).eq("user_id", userId);
        List<RelUserApp> relUserAppList = list(relUserAppQueryWrapper);
        String openId = null;
        if (relUserAppList != null && relUserAppList.size() > 0) {
            openId = relUserAppList.get(0).getOpenId();
        }
        return openId;
    }
    @Override
    public boolean createRel(String appId, String openId, int userId, String phone) {
        if (!CommonUtil.String.validStr(appId, openId) || userId == 0) {
            return false;
        }
        QueryWrapper<RelUserApp> relUserAppQueryWrapper = new QueryWrapper<>();
        relUserAppQueryWrapper.eq("app_id", appId).eq("user_id", userId).eq("open_id", openId);
        List result = list(relUserAppQueryWrapper);
        if (result != null || !result.isEmpty()) {
            return false;
        }
        RelUserApp relUserApp = new RelUserApp();
        relUserApp.setAppId(appId);
        relUserApp.setOpenId(openId);
        relUserApp.setUserId(userId);
        relUserApp.setPhone(phone);
        return save(relUserApp);
    }
}
