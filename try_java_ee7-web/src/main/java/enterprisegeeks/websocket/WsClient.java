/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.websocket;

import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.ws.rs.WebApplicationException;

/**
 * WSサーバーに接続する方。通常ブラウザから接続するが、
 * サーバー側の通知Botなどの用途や、ブラウザのエミュレートとして使える。
 */
@ClientEndpoint(encoders = SignalEncoder.class)
public class WsClient {
    
    private Session session;
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose(Session session) {
    }

    @OnMessage
    public void onMessage(String message) throws IOException, EncodeException {
    }
    
    public void notifyUpdate(){
        try {
            
            session.getBasicRemote().sendObject(Signal.UPDATE);
        } catch(IOException | EncodeException e) {
            throw new WebApplicationException(e);
        }
    }
}
