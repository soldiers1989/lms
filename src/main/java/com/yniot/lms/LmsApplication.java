package com.yniot.lms;

import com.yniot.lms.db.interceptor.ModifyInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.yniot.lms.db.dao")
public class LmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
    }


    /**
     * mybatis拦截器
     *
     * @return
     */
    @Deprecated
    @Bean
    public Interceptor getInterceptor() {
        ModifyInterceptor modifyInterceptor = new ModifyInterceptor();
        return modifyInterceptor;
    }


}
