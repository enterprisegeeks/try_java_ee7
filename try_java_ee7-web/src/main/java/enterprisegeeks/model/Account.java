/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 * ユーザーアカウント
 */
@SessionScoped
@Named
public class Account implements Serializable{
    
    @NotNull(message = "{name.required}")
    @Size(message = "{name.required}", min = 1) // 未入力の場合、空文字になるため。
    private String name;
    
    @Email(message = "{email.invalid}")
    private String email;

    private String gravaterHash;
    
    private boolean login;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getGravaterHash() {
        return gravaterHash;
    }

    public void setGravaterHash(String gravaterHash) {
        this.gravaterHash = gravaterHash;
    }
    
    public String getGravatar() {
        return "https://www.gravatar.com/avatar/" + gravaterHash;
    }
}
