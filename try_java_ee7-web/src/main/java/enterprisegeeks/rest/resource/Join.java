/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest.resource;

import enterprisegeeks.entity.Account;
import enterprisegeeks.rest.ResponseUtil;
import enterprisegeeks.rest.dto.Token;
import enterprisegeeks.service.Service;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 */
@Path("join")
@RequestScoped
@Transactional
public class Join {
 
    @Inject
    private Service service;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Token join(@Valid Account account) {
        if (service.registerAccount(account)) {
            return new Token(service.publishToken(account));
        } else {
            
            throw new WebApplicationException(ResponseUtil.buildValidErrorResponce("error", "sameaccount.exists"));
        }
    }
}
