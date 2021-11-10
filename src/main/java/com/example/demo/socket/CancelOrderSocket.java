package com.example.demo.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServerEndpoint(value = "/socket/cancelOrder/{name}")
@Component
public class CancelOrderSocket {
	
	private static Map<String,Session> sessionPool = new HashMap<>();
	
	/**
     * 发送消息方法
     * @param session 客户端与socket建立的会话
     * @param message 消息
     * @throws IOException
     */
    protected void sendMessage(Session session, String message) throws IOException{
        if(session != null)
        {
            session.getBasicRemote().sendText(message);
        }
    }
	
	@OnOpen
    public void onOpen(Session session,@PathParam(value = "name")String userName){
        sessionPool.put(userName, session);
    }
	
	/**
     * 关闭连接时调用
     * @param userName 关闭连接的客户端的姓名
     */
    @OnClose
    public void onClose(@PathParam(value = "name") String userName){
        sessionPool.remove(userName);
    }
	
	public void sendInfo(String userName){
        Session session = sessionPool.get(userName);
        try {
            sendMessage(session, "cancelOrder");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
