package com.yniot.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yniot.lms.db.dao.MessageMapper;
import com.yniot.lms.db.entity.Message;
import com.yniot.lms.service.MessageService;
import com.yniot.lms.websocket.LmsWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: lane
 * @Date: 2018/11/26 11:48
 * @Description:
 * @Version 1.0.0
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    LmsWebSocketServer lmsWebSocketServer;


    @Override
    public void sendMessage(String jsonContent, int receiver, int type, int templateId) {
        Message message = new Message();
        message.setContent(jsonContent);
        message.setCreateTime(new Date());
        message.setReceiver(receiver);
        message.setMsgType(type);
        message.setTemplateId(templateId);
        super.save(message);
        lmsWebSocketServer.sendWSMessage(message.getContent(), message.getReceiver());
    }

}
