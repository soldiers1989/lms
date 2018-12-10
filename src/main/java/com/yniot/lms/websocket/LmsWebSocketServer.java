package com.yniot.lms.websocket;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 21:11
 **/
@ServerEndpoint(LmsWebSocketServer.BASE_PATH + "/{token}")
@Component
public class LmsWebSocketServer extends BaseWebsocket {
    private static Logger logger = Logger.getLogger(LmsWebSocketServer.class);

    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) {
        this.session = session;
        webSockets.add(this);
        logger.info("新建来自token为[" + token + "]的连接,当前连接数:" + webSockets.size());
    }

    @OnClose
    public void onClose() {
        logger.info("关闭连接:" + session.getRequestURI().getPath());
        this.removeSession(session.getId());
        logger.info("当前连接数:" + webSockets.size());
    }


    private boolean removeSession(String sessionId) {
        for (LmsWebSocketServer temp : webSockets) {
            if (sessionId.equals(temp.session.getId())) {
                webSockets.remove(temp);
                return true;
            }
        }
        return false;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("接受信息:" + session.getRequestURI().getPath());
        logger.info("信息内容:" + message);

    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误:" + session.getRequestURI().getPath());
        logger.error(error.getMessage());

    }


}
