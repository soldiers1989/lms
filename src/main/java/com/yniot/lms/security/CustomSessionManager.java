package com.yniot.lms.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @Auther: lane
 * @Date: 2018-12-19 16:38
 * @Description:
 * @Version 1.0.0
 */
public class CustomSessionManager extends DefaultWebSessionManager {
    /**
     * 这个是服务端要返回给客户端，
     */
    public final static String TOKEN_NAME = "token";
    /**
     * 这个是客户端请求给服务端带的header
     */
    public final static String HEADER_TOKEN_NAME = "Authorization";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
    private static Logger logger = Logger.getLogger(CustomSessionManager.class);

    /**
     * 重写getSessionId,分析请求头中的指定参数，做用户凭证sessionId
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(HEADER_TOKEN_NAME);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        logger.info("request uri:" + url);
        if (StringUtils.isEmpty(id)) {
            //如果没有携带id参数则按照父类的方式在cookie进行获取
            return super.getSessionId(request, response);
        } else {
            //如果请求头中有 authToken 则其值为sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
    }

}
