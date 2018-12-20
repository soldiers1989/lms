package com.yniot.lms.security;

import com.alibaba.fastjson.JSONObject;
import com.yniot.lms.controller.UserController;
import com.yniot.lms.controller.commonController.BaseController;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: lane
 * @Date: 2018-12-20 08:57
 * @Description: 停用shiro在未登录时的重定向操作，现在改为返回401
 * @Version 1.0.0
 */
public class CustomFormAuthFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        if (UserController.LOGIN_FULL_URL.equals(url)) {
            return true;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(BaseController.RESULT_KEY, false);
            jsonObject.put(BaseController.STATUS_KEY, BaseController.NEED_LOGIN);
            response.getWriter().write(jsonObject.toJSONString());
            return false;
        }
    }

    @Bean
    public FilterRegistrationBean registration(CustomFormAuthFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
