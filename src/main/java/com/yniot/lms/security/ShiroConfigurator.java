package com.yniot.lms.security;

import org.apache.log4j.Logger;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfigurator {
    private static Logger logger = Logger.getLogger(ShiroConfigurator.class);

    @Bean
    public ShiroFilterFactoryBean shirFilter(org.apache.shiro.web.mgt.DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**", "anon");//静态资源
        filterChainDefinitionMap.put("/druid/**", "anon");//druid管理页面
        filterChainDefinitionMap.put("/WeChat/**", "anon");//微信
        filterChainDefinitionMap.put("/SmallAppApi/**", "anon");//微信
        //SmallAppApi
        filterChainDefinitionMap.put("/user/login", "anon");//登陆
        filterChainDefinitionMap.put("/WebSocket/**", "anon");//WebSocket
        filterChainDefinitionMap.put("/favicon.ico", "anon");//书签logo
        filterChainDefinitionMap.put("/**", "authc");


        shiroFilterFactoryBean.setLoginUrl("/pages/auth/login/login");
//        shiroFilterFactoryBean.setSuccessUrl("/user/index");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * shiro 用户数据注入
     *
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    /**
     * 配置管理层。即安全控制层
     *
     * @return
     */
    @Bean
    public org.apache.shiro.web.mgt.DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }


    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.web.mgt.DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @Bean
    public SessionManager sessionManager() {
        CustomSessionManage customSessionManage = new CustomSessionManage();
        return customSessionManage;
    }

}
