/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest;

import enterprisegeeks.rest.anotation.MessageResource;
import enterprisegeeks.rest.anotation.WithAuth;
import enterprisegeeks.entity.Account;
import enterprisegeeks.rest.dto.Authed;
import enterprisegeeks.service.Service;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * 認証チェックインターセプター
 */
@Interceptor
@Dependent
@WithAuth
@Transactional
public class WithAuthInterceptor implements Serializable{

    @Inject
    private HttpServletRequest req;
    
    @Inject
    private Service service;
    
    @Inject @MessageResource
    private ResourceBundle message;
    
    @Inject
    private Authed authed;
    
    
    @AroundInvoke
    public Object invoke(InvocationContext ic) throws Exception{
        if (req == null) {
            forbidden();
        }
        
        String token = req.getHeader("authorization");
        if (token == null) {
            forbidden();
        }
        Account account = null;
        try {
            account = service.getAccountByToken(token);
        } catch(EntityNotFoundException ex) {
            forbidden();
        }
        // 後続の処理で使用するため、アカウントを設定。
        authed.setAccount(account);
        
        return ic.proceed();
    }
    
    private void forbidden() {
        throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN)
                .entity(message.getString("forbidden")).build());
    }
}
