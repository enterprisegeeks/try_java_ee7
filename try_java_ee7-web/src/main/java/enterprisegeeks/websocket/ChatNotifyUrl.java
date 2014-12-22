/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.websocket;

import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Dependent
public class ChatNotifyUrl {
    @Inject
    private HttpServletRequest req;
    
    @Produces @ChatNotifyEndPoint
    public URI getUri(){
        
        try {
            return new URI(String.format("ws://%s:%s%s/chat_notify", req.getServerName()
                    , req.getServerPort(), req.getContextPath()));
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
