/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.service;

import enterprisegeeks.websocket.ChatNotifyEndPoint;
import enterprisegeeks.websocket.Signal;
import enterprisegeeks.websocket.WsClient;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.ws.rs.WebApplicationException;

/**
 * チャット更新通知
 */
@Dependent
public class ChatNotifier implements Serializable {
    
    @Inject @ChatNotifyEndPoint
    private String wsURI;
    
    @Inject
    private Event<Signal> event;
    
    public void notifyNewChat(){
        System.out.println("fire");
        event.fire(Signal.UPDATE);
    }
}
