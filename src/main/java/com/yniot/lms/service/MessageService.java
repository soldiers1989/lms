package com.yniot.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yniot.lms.db.entity.Message;

/**
 * @Auther: lane
 * @Date: 2018/11/26 11:47
 * @Description:
 * @Version 1.0.0
 */
public interface MessageService extends IService<Message> {
    public void sendMessage(String jsonContent, int receiver, int type, int templateId);
}
