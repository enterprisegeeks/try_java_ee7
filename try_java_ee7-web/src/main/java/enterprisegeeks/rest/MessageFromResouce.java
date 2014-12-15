/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest;

import java.util.ResourceBundle;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * メッセージファイルを読込むプロデューサー
 */
@ApplicationScoped
public class MessageFromResouce {
    
    @Produces @Message
    public ResourceBundle read() {
        return ResourceBundle.getBundle("msg");
    }
}
