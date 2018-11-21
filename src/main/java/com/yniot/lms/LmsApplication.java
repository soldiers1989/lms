package com.yniot.lms;

import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.db.interceptor.ModifyInterceptor;
import com.yniot.lms.service.WeChatService;
import com.yniot.lms.service.wechat.WeChatImageHandler;
import com.yniot.lms.service.wechat.WeChatLogHandler;
import com.yniot.lms.service.wechat.WeChatOAuth2Handler;
import com.yniot.lms.service.wechat.WeChatTextHandler;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceHttpClientImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//
@EnableTransactionManagement
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
@MapperScan("com.yniot.lms.db.dao")
public class LmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
    }



    /**
     * mybatis拦截器
     * @return
     */
    @Bean
    public Interceptor getInterceptor() {
        ModifyInterceptor modifyInterceptor = new ModifyInterceptor();
        return modifyInterceptor;
    }






}
