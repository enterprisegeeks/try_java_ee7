/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.websocket;

import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * websocketのサーバーサイド実装
 * 
 * サーバーサイドから更新通知を受け取ると、全てのクライアントに対して更新通知を発行する。
 */
@ServerEndpoint(value="/chat_notify", decoders = SignalDecoder.class)
public class WsEndPoint {
  
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + "was connect.");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(session.getId() + "was disconnnet.");
    }

    @OnMessage
    public void onMessage(Signal sign, Session client) throws IOException, EncodeException {
        if (sign == Signal.UPDATE) {
            for (Session otherSession : client.getOpenSessions()) {
                    otherSession.getBasicRemote().sendText("");
            }
        }
    }
    
    
}
