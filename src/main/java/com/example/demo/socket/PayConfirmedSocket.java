package com.example.demo.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServerEndpoint(value = "/socket/payConfirmed/{name}")
@Component
public class PayConfirmedSocket {
	
	private static AtomicInteger online = new AtomicInteger();
	
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
    
    public static void addOnlineCount(){
        online.incrementAndGet();
    }
    
    @OnOpen
    public void onOpen(Session session,@PathParam(value = "name")String userName){
        sessionPool.put(userName, session);
        addOnlineCount();
        System.out.println(userName + "加入webSocket！当前人数为" + online);
    }
    
    public static void subOnlineCount() {
        online.decrementAndGet();
    }
    
    /**
     * 关闭连接时调用
     * @param userName 关闭连接的客户端的姓名
     */
    @OnClose
    public void onClose(@PathParam(value = "name") String userName){
        sessionPool.remove(userName);
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + online);
    }
    
    /**
     * 给指定用户发送消息
     * @param userName 用户名
     * @param message 消息
     * @throws IOException
     */
    public void sendInfo(String userName, String message){
        Session session = sessionPool.get(userName);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendInfo(String userName){
        Session session = sessionPool.get(userName);
        try {
            sendMessage(session, "payConfirmed");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
