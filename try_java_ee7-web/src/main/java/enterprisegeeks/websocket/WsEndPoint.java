/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.websocket;

import enterprisegeeks.util.Executor;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
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
  
    @Inject @Executor
    private ManagedExecutorService es;
    
    @Inject
    private Logger log;
    
    // 全てのセッションをフィールドとして保持するなら、staticにする。(ドキュメントに書いてある)
    // コンテナはコネクションごとにエンドポイントのインスタンスを作るため。
    // よって、スレッドセーフにしておくこと。
    // concurrentHashSetはないので、Collectnons.newSetFromMapが楽。
    private static Set<Session> sessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());

    @OnOpen
    public void onOpen(Session session) {
        log.info(() -> session.getId() + " is connected");
        sessions.add(session);
    }
    
    
    @OnClose
    public void onClose(Session session) {
        log.info(() -> session.getId() + " is closed");
        sessions.remove(session);
    }
    
    public void notifyUpdate(@Observes Signal sign) {
        System.out.println("recieve:" + sessions.size());
        
        if (sign == Signal.UPDATE) {
            for (Session otherSession : sessions) {
                es.submit(() -> {
                    try {
                        otherSession.getBasicRemote().sendText("s");
                        log.info("send to" + otherSession.getId());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        }
    }
    
    @OnMessage
    public void onMessage(Signal sign, Session client) throws IOException, EncodeException {
        
    }
}
