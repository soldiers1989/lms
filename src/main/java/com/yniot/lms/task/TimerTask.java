package com.yniot.lms.task;

import com.yniot.lms.service.MessageService;
import com.yniot.lms.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TimerTask {
    private static Logger logger = Logger.getLogger(TimerTask.class);

    @Autowired
    OrderService orderService;
    @Autowired
    MessageService messageService;

    @Scheduled(cron = "0 0/1 * * * ? ") // 每分钟查找过期的订单
    public void logCacheKeys() {
        this.markExpiredOrder();
    }


    @Scheduled(cron = "0/30 * * * * ? ")
    public void sendMessageTest() {
        messageService.sendMessage("message!!!", 9527, 1, 1);
    }


    private void markExpiredOrder() {
        int cnt = orderService.markExpiredOrder();
        logger.info("过期的订单数量:[" + cnt + "]");
    }
}
