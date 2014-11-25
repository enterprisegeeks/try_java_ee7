/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.model;

import enterprisegeeks.entity.Account;
import enterprisegeeks.entity.Room;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * 認証済みアカウント
 */
@SessionScoped
@Named
public class Auth implements Serializable{
    
    private boolean loggedIn;
    
    private Room selected;
    
    private Account account = new Account();

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Room getSelected() {
        return selected;
    }

    public void setSelected(Room selected) {
        this.selected = selected;
    }
    
    
}
