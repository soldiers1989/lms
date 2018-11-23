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
@ServerEndpoint("/WebSocket/wardrobe/{userId}")
@Component
public class LmsWebSocketServer {
    private static Logger logger = Logger.getLogger(LmsWebSocketServer.class);

    @OnOpen
    public void onOpen(@PathParam("userId") int userId, Session session) {
        logger.info("userId:" + userId);

    }

    @OnClose
    public void onClose() {

    }


    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("message:" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {


    }


}
