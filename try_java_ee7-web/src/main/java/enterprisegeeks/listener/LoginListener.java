/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.listener;

import enterprisegeeks.entity.Account;
import enterprisegeeks.model.Auth;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *ログイン済みかどうかを判定し、遷移先を決定する。
 */
@RequestScoped
@Named("loginListener")
public class LoginListener{
    
    @Inject
    private Auth auth;
    
    private boolean isLoggedin() {
        return auth != null && auth.isLoggedIn();
    }
    
    /** ログイン済みで無い場合、ログインページへリダイレクト */
    public void requireLogin() {
        if (!isLoggedin()) {
            
         ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler)
                FacesContext.getCurrentInstance()
                    .getApplication().getNavigationHandler();
            handler.performNavigation("index");
        }
    }
    
    /** ログイン済みでログインページにきた場合は、ログイン後ページへリダイレクト */
    public void skipLogin() {
        if(isLoggedin()) {
            ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler)
                FacesContext.getCurrentInstance()
                    .getApplication().getNavigationHandler();
            handler.performNavigation("chatroom");
        }
         
    }
    
}
