/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest.dto;

import enterprisegeeks.entity.Account;
import javax.enterprise.context.RequestScoped;

/**
 * 認証済み情報
 */
@RequestScoped
public class Authed {
    
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    
}
