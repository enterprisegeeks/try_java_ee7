/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.action;

import enterprisegeeks.entity.Account;
import enterprisegeeks.model.Auth;
import enterprisegeeks.service.Service;
import enterprisegeeks.util.Gravater;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@RequestScoped
@Named
@Transactional
public class AuthAction {
    
    @Inject
    private Auth auth;
    
    @Inject
    private Service service;
    
    
    /** 初期画面からログインする */
    public String enter() {
        Account account = auth.getAccount();
        account.setGravaterHash(Gravater.md5Hex(account.getEmail()));
        
        if (service.registerAccount(account)) {
            
            auth.setLoggedIn(true);
            return "chatroom.xhtml?faces-redirect=true";
        } else {
            //エラーメッセージ設定 
            FacesContext fc = FacesContext.getCurrentInstance();
            String message = fc.getApplication().getResourceBundle(fc, "msg")
                    .getString("sameaccount.exists");
            fc.addMessage("error", new FacesMessage(message));
            return null;
        }
    }
    
    
    /** ログアウト */
    public String exit() {
        
        
        auth.setLoggedIn(false);
        auth.setAccount(new Account());
        
        HttpSession session = (HttpSession)FacesContext
                .getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        
        return "index.xhtml?faces-redirect=true";
    }
}
