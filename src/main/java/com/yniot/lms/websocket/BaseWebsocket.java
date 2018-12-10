package com.yniot.lms.websocket;

import com.alibaba.fastjson.JSONObject;
import com.yniot.lms.enums.WebSocketEnum;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Auther: lane
 * @Date: 2018-12-10 09:39
 * @Description:
 * @Version 1.0.0
 */
public class BaseWebsocket {
    protected Session session;
    protected static CopyOnWriteArraySet<LmsWebSocketServer> webSockets = new CopyOnWriteArraySet<>();
    public static final String BASE_PATH = "/WebSocket";

    private String message(Object data, String type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("data", jsonObject);
        return jsonObject.toJSONString();
    }

    public String Shipment(Object data) {
        return this.message(data, WebSocketEnum.Shipment.getType());
    }

    /*
     * @Author wanggl(lane)
     * @Description //TODO 发送消息
     * @Date 上午11:41 2018/11/26
     * @Param [jsonMessage, token]
     * @return void
     **/
    public void sendWSMessage(String jsonMessage, String token) {
        for (LmsWebSocketServer webSocket : webSockets) {
            Session session = webSocket.session;
            Map<String, String> pathMap = session.getPathParameters();
//            int id = Integer.valueOf(pathMap.get("token"));
            if (session.isOpen()) {
                String path = webSocket.session.getRequestURI().getPath();
                if (path.equals(BASE_PATH + "/" + token)) {
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
