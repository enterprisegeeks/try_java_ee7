/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.websocket;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
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
@Dependent
public class WsEndPoint {
  
    @Inject
    private ExecutorService es;
    
    @OnOpen
    public void onOpen(Session session) {
    }

    @OnClose
    public void onClose(Session session) {
    }

    @OnMessage
    public void onMessage(Signal sign, Session client) throws IOException, EncodeException {
        if (sign == Signal.UPDATE) {
            // 非同期送信
            for (final Session otherSession : client.getOpenSessions()) {
                es.submit(() -> {
                    try {
                        otherSession.getBasicRemote().sendText("");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        }
    }
}
