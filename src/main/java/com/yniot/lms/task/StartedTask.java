package com.yniot.lms.task;

import org.apache.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动任务
 */
@Component
@Order(1)
public class StartedTask implements ApplicationRunner {
    private static Logger logger = Logger.getLogger(StartedTask.class);



    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initCacheMethod();
    }

    private void initCacheMethod() {
//        logger.info("*********************加载系统配置到缓存*********************");
//        systemConfigService.getConfig();
//        logger.info("*********************加载需要拦截的mybatis的dao方法列表到缓存*********************");
//        systemConfigService.initMybatisInterceptorCache();
//        logger.info("*********************加载告警类型到缓存*********************");
//        warningService.loadWarningTypeToCache();
//        logger.info("*********************缓存加载完成*********************");
    }


}
