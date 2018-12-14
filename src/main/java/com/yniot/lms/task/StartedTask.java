package com.yniot.lms.task;

import com.yniot.lms.controller.commonController.BaseWxController;
import com.yniot.lms.db.entity.WeChatConfig;
import com.yniot.lms.service.SmallAppService;
import com.yniot.lms.service.WeChatService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
        this.initWeChatConfig();
    }


    @Autowired
    WeChatService weChatService;
    @Autowired
    SmallAppService smallAppService;

    private void initWeChatConfig() {
        WeChatConfig weChatConfig = weChatService.getConfig();
        smallAppService.initConfig();
        BaseWxController.initConfig(weChatService);
        if (weChatConfig != null) {
            logger.info("微信配置加载完成!");
        } else {
            logger.info("微信配置加载失败,退出程序!");
            System.exit(0);
        }
    }


}
