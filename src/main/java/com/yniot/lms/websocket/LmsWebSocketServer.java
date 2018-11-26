package com.yniot.lms.websocket;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 21:11
 **/
@ServerEndpoint(LmsWebSocketServer.BASE_PATH + "/{userId}")
@Component
public class LmsWebSocketServer {
    private static Logger logger = Logger.getLogger(LmsWebSocketServer.class);
    private Session session;
    private static CopyOnWriteArraySet<LmsWebSocketServer> webSockets = new CopyOnWriteArraySet<>();
    public static final String BASE_PATH = "/WebSocket";

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

    /*
     * @Author wanggl(lane)
     * @Description //TODO 发送消息
     * @Date 上午11:41 2018/11/26
     * @Param [jsonMessage, userId]
     * @return void
     **/
    public void sendWSMessage(String jsonMessage, int userId) {
        for (LmsWebSocketServer webSocket : webSockets) {
            String path = webSocket.session.getRequestURI().getPath();
            if (path.equals(BASE_PATH + "/" + userId)) {
                try {
                    webSocket.session.getBasicRemote().sendText(jsonMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    break;
                }
            }

        }
    }

}
