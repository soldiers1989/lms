//package com.yniot.lms.security;
//
//import com.alibaba.fastjson.JSONObject;
//import com.yniot.lms.controller.commonController.BaseController;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.web.servlet.AdviceFilter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * @Auther: lane
// * @Date: 2018/12/4 10:12
// * @Description:
// * @Version 1.0.0
// */
//public class CustomAdviceFilter extends AdviceFilter {
//    public static final String SMALL_APP_LOGIN = "/SmallAppApi/login";
//    public static final String LOGIN = "/user/login";
//
//
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws IOException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String uri = httpServletRequest.getRequestURI();
//        if (uri.contains(LOGIN)) {//登录操作则直接放行
//            return true;
//        }
////        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
////        String header = httpServletRequest.getHeader("Content-Type");
////        String token = httpServletRequest.getHeader(BaseController.AUTHORIZATION);
//        //&& header.equals("application/x-www-form-urlencoded")
//        if (StringUtils.isEmpty(token)) {//未登录的ajax请求  没有判断头部信息,因为目前全部为ajax请求
//            response.setCharacterEncoding("UTF-8");
//            PrintWriter out = response.getWriter();
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put(BaseController.RESULT_KEY, false);
//            jsonObject.put(BaseController.STATUS_KEY, BaseController.NEED_LOGIN);
//            out.print(jsonObject.toJSONString());
//            out.flush();
//            out.close();
//            return false;
//        }
//        return true;
//    }
//}
