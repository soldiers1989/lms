package com.yniot.lms.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @Auther: lane
 * @Date: 2018/11/29 15:53
 * @Description:
 * @Version 1.0.0
 */
public class CustomSessionManage extends DefaultWebSessionManager {
    //token的key
    private static final String AUTHORIZATION = "Authorization";
    //无状态
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public CustomSessionManage() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (StringUtils.isNotEmpty(token)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        }
        return super.getSessionId(request, response);
    }
}
