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
@ServerEndpoint(LmsWebSocketServer.BASE_PATH + "/{receiverId}")
@Component
public class LmsWebSocketServer {
    private static Logger logger = Logger.getLogger(LmsWebSocketServer.class);
    private Session session;
    private static CopyOnWriteArraySet<LmsWebSocketServer> webSockets = new CopyOnWriteArraySet<>();
    public static final String BASE_PATH = "/WebSocket";

    @OnOpen
    public void onOpen(@PathParam("receiverId") String receiverId, Session session) {
        this.session = session;
        webSockets.add(this);
        logger.info("新建来自receiverId为[" + receiverId + "]的连接,当前连接数:" + webSockets.size());
    }

    @OnClose
    public void onClose() {
        logger.info("关闭连接:" + session.getRequestURI().getPath());
        logger.info("当前连接数:" + webSockets.size());

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

    /*
     * @Author wanggl(lane)
     * @Description //TODO 发送消息
     * @Date 上午11:41 2018/11/26
     * @Param [jsonMessage, receiverId]
     * @return void
     **/
    public void sendWSMessage(String jsonMessage, int receiverId) {
        for (LmsWebSocketServer webSocket : webSockets) {
            Session session = webSocket.session;
            int id = Integer.valueOf((String) session.getPathParameters(""));
            if (session.isOpen()) {
                String path = webSocket.session.getRequestURI().getPath();
                if (path.equals(BASE_PATH + "/" + receiverId)) {
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

}
