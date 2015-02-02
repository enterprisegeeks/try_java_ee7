/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest;

import enterprisegeeks.rest.anotation.MessageResource;
import enterprisegeeks.rest.dto.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 */
@ApplicationScoped
public class ResponseUtil{
    
    @Inject
    @MessageResource
    private ResourceBundle messages;
    
    /*
     * バリデーションエラー時に通知するレスポンスを作成する。
    */
    public Response buildValidErrorResponce(String key, String messageKey) {
        
        String message = messages.getString(messageKey);
        
        return Response.status(400).type(MediaType.APPLICATION_JSON)
                .entity(new Message[]{Message.of(key, message)}).build();
        
    }
}
