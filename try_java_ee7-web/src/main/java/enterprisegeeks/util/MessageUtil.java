/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.util;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * メッセージ取得
 */
@ApplicationScoped
public class MessageUtil {
    
    public void addMessage(String cliendId, String messageId) {
         FacesContext fc = FacesContext.getCurrentInstance();
         String message = fc.getApplication().getResourceBundle(fc, "msg")
                 .getString(messageId);
         fc.addMessage(cliendId, new FacesMessage(message));
    }
    
}
