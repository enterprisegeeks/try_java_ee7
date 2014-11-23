/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.action;

import enterprisegeeks.model.Account;
import enterprisegeeks.util.Gravater;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@RequestScoped
@Named
public class Action {
    
    @Inject
    private Account account;
    
    public String join() {
        if (account.getEmail() != null){
            account.setGravaterHash(Gravater.md5Hex(account.getEmail()));
        }
        account.setLogin(true);
        
        return "chatroom";
    }
    
    public String exit() {
        
        
        account.setLogin(false);
        account.setName(null);
        account.setEmail(null);
        
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        
        return "/index.xhtml";
    }
}
