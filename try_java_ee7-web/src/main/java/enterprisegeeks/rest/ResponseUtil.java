/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest;

import enterprisegeeks.rest.dto.Messages;
import java.util.ResourceBundle;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 */
public class ResponseUtil{
    
    /*
     * バリデーションエラー時に通知するレスポンスを作成する。
    */
    public static Response buildValidErrorResponce(String key, String messageKey) {
        
        // message
        ResourceBundle rb = ResourceBundle.getBundle("msg");
        String message = rb.getString(messageKey);
        Messages ms = new Messages();
        ms.addMessage(key, message);
        return Response.status(400).type(MediaType.APPLICATION_JSON).entity(ms).build();
        
    }
}
